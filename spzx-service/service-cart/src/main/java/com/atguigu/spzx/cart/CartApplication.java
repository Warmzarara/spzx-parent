package com.atguigu.spzx.cart;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.cart
 * @className: CartApplication
 * @author: XiaoHB
 * @date: 2024/3/9 14:14
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //排除数据库自动化配置
@EnableFeignClients(basePackages = {"com.atguigu.spzx"})
@EnableUserLoginAuthInterceptor
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class,args);
    }
}
