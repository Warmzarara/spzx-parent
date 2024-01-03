package com.atguigu.spzx.manager.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.UserInfo;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.RedisPrefixEnum;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.interceptor
 * @className: LoginAuthInterceptor
 * @author: XiaoHB
 * @date: 2023/12/20 14:51
 */
public class LoginAuthInterceptor implements HandlerInterceptor {
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    /**
     * 在所有方法执行之前执行该方法
     * 1.获取请求方式，如果是options（预检请求）则放行
     * 2.从请求头获取token
     * 3.如果token为空，返回异常
     * 4.如果token不为空，带着token去查redis
     * 5.若查不到，返回异常
     * 6.将用户信息放到ThreadLocal中
     * 7.将redis中的用户数据更新一下过期时间
     * 8.放行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if("OPTIONS".equals(method)){
            return true;
        }
        String token = request.getHeader("token");
        if(StrUtil.isEmpty(token)){
            responseNoLoginInfo(response);
            return false;
        }
        String userInfoString = stringRedisTemplate.opsForValue().get(RedisPrefixEnum.USER_LOGIN.getPrefix() + token);
        if(StrUtil.isEmpty(userInfoString)){
            responseNoLoginInfo(response);
            return false;        
        }
        SysUser sysUser = JSON.parseObject(userInfoString,SysUser.class);
        AuthContextUtil.set(sysUser);
        stringRedisTemplate.expire(RedisPrefixEnum.USER_LOGIN.getPrefix()+token,30, TimeUnit.MINUTES);
        return true;
    }

    /**
     * 在所有方法执行之后执行该方法
     * 删除ThreadLocal对象
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        AuthContextUtil.remove();
    }


    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }
}
