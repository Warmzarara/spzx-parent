package com.atguigu.spzx.pay;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.atguigu.spzx.common.anno.EnableUserTokenFeignInterceptor;
import com.atguigu.spzx.pay.utils.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.pay
 * @className: PayApplication
 * @author: XiaoHB
 * @date: 2024/3/15 18:05
 */
@SpringBootApplication
@EnableUserLoginAuthInterceptor
@EnableUserTokenFeignInterceptor
@EnableFeignClients(basePackages = {"com.atguigu.spzx"})
@EnableConfigurationProperties(value = {AlipayProperties.class})
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class,args);
    }
}
