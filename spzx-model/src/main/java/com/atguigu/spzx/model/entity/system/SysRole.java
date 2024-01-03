package com.atguigu.spzx.model.entity.system;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "SysRole")
/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.entity.system
 * @className: SysRole
 * @author: XiaoHB
 * @date: 2023/12/24 15:28
 */
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "描述")
    private String description;

}