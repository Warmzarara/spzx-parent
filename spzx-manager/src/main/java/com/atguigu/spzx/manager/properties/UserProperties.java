package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.properties
 * @className: UserProperties
 * @author: XiaoHB
 * @date: 2023/12/20 16:11
 * 1.在applicatoin.yml中编写配置信息
 * 2.创建配置类，添加@Data和@ConfigurationProperties注解
 * 3.在启动类中注册配置类
 * 4.注入并使用
 */

@Data
@ConfigurationProperties(prefix = "spzx.auth")
public class UserProperties { 
    private List<String> noAuthUrls;
}
