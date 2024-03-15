package com.atguigu.spzx.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.gateway.filter
 * @className: AuthGlobalFilter
 * @author: XiaoHB
 * @date: 2024/3/7 17:37
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 1.获取请求路径
     * 2.判断路径是否符合规则，登录校验
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /*
        获取路径
         */
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();

        if (antPathMatcher.match("/api/**/auth/**",path)) {
            UserInfo userInfo = this.getUserInfo(request);
            //没有用户信息，未登录状态，拦截
            if(userInfo==null){
                return out(response,ResultCodeEnum.LOGIN_AUTH);
            }
        }
        
        /*放行*/
        return chain.filter(exchange);
    }

    private UserInfo getUserInfo(ServerHttpRequest request) {
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if(tokenList != null){
            token = tokenList.get(0);
        }
        if(!StrUtil.isEmpty(token)){
            String userJson = redisTemplate.opsForValue().get("user:spzx:" + token);
            if(StrUtil.isEmpty(userJson)){
                return null;
            } else {
                return JSON.parseObject(userJson, UserInfo.class);
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
    
    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result<Object> result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
