package com.atguigu.spzx.model.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.dto.system
 * @className: AssignMenuDto
 * @author: XiaoHB
 * @date: 2024/1/11 19:13
 */
@Data
@Schema(description = "请求参数实体类")
public class AssignMenuDto {
    @Schema(description = "角色id")
    private Long roleId;
    
    @Schema(description = "选中的菜单id的集合")
    private List<Map<String,Object>> menuIdList;    // key1:id  value1:id值   key2:isHalf value2:0/1 0:菜单全选 1:菜单部分选择
}
