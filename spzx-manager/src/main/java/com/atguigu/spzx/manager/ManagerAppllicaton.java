package com.atguigu.spzx.manager;

import com.atguigu.spzx.common.log.annotation.EnableLogAspect;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.properties.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.spzx"})
//加载配置文件
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})
//开启定时任务
@EnableScheduling
@EnableLogAspect
public class ManagerAppllicaton {
    public static void main(String[] args) {
        SpringApplication.run(ManagerAppllicaton.class,args);
    }
}
