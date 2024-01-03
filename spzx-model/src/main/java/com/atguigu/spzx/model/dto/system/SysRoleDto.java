package com.atguigu.spzx.model.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.dto.system
 * @className: SysRoleDto
 * @author: XiaoHB
 * @date: 2023/12/24 15:06
 */
@Data
@Schema(description = "请求参数实体类")
public class SysRoleDto {
    @Schema(description = "角色名称")
    private String roleName ;
}
