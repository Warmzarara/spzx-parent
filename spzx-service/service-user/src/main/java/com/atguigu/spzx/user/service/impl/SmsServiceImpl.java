package com.atguigu.spzx.user.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.util.StrUtil;
import com.atguigu.spzx.user.service.SmsService;
import com.atguigu.spzx.user.utils.HttpUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.user.service.impl
 * @className: SmsServiceImpl
 * @author: XiaoHB
 * @date: 2024/3/5 18:01
 */
@Service
public class SmsServiceImpl implements SmsService {
    
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    
    /**
     * 1.生成验证码
     * 2.把生成的验证码放到redis中，设计过期时间
     * 3.向手机号发送短信验证码
     * @param phone
     */
    @Override
    public void getCode(String phone) {

        String code = redisTemplate.opsForValue().get(phone);
        if (!StrUtil.isEmpty(code)){
            return;
        }
        //生成随机4位验证码
        code = RandomStringUtils.randomNumeric(4);
        redisTemplate.opsForValue().set("phone:code:" + phone,code);
        sendMessage(phone,code);
    }
    
    private void sendMessage(String phone,String code){
        /*
        域名、路径、提交方式 不可修改
         */
        String host = "https://cxkjsms.market.alicloudapi.com";
        String path = "/chuangxinsms/dxjk";
        String method = "POST";
        
        String appcode = "a90726a286bc402796999aaa3a197ae6";//开通服务后 买家中心-查看AppCode
        
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("content", "【创信】你的验证码是：5873，3分钟内有效！");
        querys.put("mobile", phone);
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
