package com.atguigu.spzx.model.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.dto.system
 * @className: AssignDto
 * @author: XiaoHB
 * @date: 2024/1/8 18:03
 */
@Data
@Schema(description = "用户请求参数实体类")
public class AssignRoleDto {
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "用户id的List集合")
    private List<Long> roleIdList;
}
