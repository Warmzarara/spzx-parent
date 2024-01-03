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
 */

@Data
@ConfigurationProperties(prefix = "spzx.auth")
public class UserProperties { 
    private List<String> noAuthUrls;
}
