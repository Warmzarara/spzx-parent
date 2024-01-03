package com.atguigu.spzx.model.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.dto.system
 * @className: SysUserDto
 * @author: XiaoHB
 * @date: 2023/12/30 16:37
 */
@Data
@Schema(description = "请求参数实体类")
public class SysUserDto {
    
    @Schema(description = "搜索关键字")
    private String keyword;
    
    @Schema(description = "开始时间")
    private String createTimeBegin;
    
    @Schema(description = "结束时间")
    private String createTimeEnd;
}
