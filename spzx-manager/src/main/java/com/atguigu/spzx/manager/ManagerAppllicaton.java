package com.atguigu.spzx.manager;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.common.log.annotation.EnableLogAspect;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.properties.UserProperties;
import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.manager.utils.BeanUtils;
import com.atguigu.spzx.manager.utils.BeanUtilsA;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.ContextLoader;


@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.spzx"})
//加载配置文件
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})
//开启定时任务
@EnableScheduling
@EnableLogAspect
public class ManagerAppllicaton {
    public static void main(String[] args) {
        // TODO: 2024/3/1 这里试了一下获取Bean容器 
        ConfigurableApplicationContext app = SpringApplication.run(ManagerAppllicaton.class, args);
        System.out.println(app.getBean("brandServiceImpl"));
        
//        BeanUtils.setApplicationContext(app);
//        try {
//            System.out.println("getBean----> "+BeanUtils.getBean("brandServiceImpl"));
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new GuiguException(ResultCodeEnum.BEAN_ERROR);
//        }
    }
}
