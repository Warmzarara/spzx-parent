package com.atguigu.spzx.model.dto.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.dto.h5
 * @className: UserLoginDto
 * @author: XiaoHB
 * @date: 2024/3/7 16:29
 */
@Data
@Schema(description = "用户登录请求参数")
public class UserLoginDto {

    @Schema(description = "用户名")
    private String username ;

    @Schema(description = "密码")
    private String password ;
}