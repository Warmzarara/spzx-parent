package com.atguigu.spzx.user;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.user
 * @className: UserApplication
 * @author: XiaoHB
 * @date: 2024/3/5 18:25
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.spzx"})
@EnableUserLoginAuthInterceptor
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
