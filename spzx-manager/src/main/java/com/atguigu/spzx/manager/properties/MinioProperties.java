package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.properties
 * @className: MinioProperties
 * @author: XiaoHB
 * @date: 2024/1/5 19:31
 * 1.在applicatoin.yml中编写配置信息
 * 2.创建配置类，添加@Data和@ConfigurationProperties注解
 * 3.在启动类中注册配置类
 */
@Data
@ConfigurationProperties(prefix = "spzx.minio")
public class MinioProperties {
    private String endpointUrl;
    private String accessKey;
    private String secreKey;
    private String bucketName;
}
