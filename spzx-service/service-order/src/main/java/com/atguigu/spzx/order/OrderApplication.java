package com.atguigu.spzx.order;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.atguigu.spzx.common.anno.EnableUserTokenFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.order
 * @className: OrderApplication
 * @author: XiaoHB
 * @date: 2024/3/10 14:58
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.atguigu.spzx"})
@EnableUserTokenFeignInterceptor
@EnableUserLoginAuthInterceptor
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
