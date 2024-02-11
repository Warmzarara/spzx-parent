package com.atguigu.spzx.model.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.dto.order
 * @className: OrderStatisticsDto
 * @author: XiaoHB
 * @date: 2024/2/5 19:17
 */
@Data
@Schema(description = "搜索条件实体类")

public class OrderStatisticsDto {
    @Schema(description = "开始时间")
    private String createTimeBegin;
    
    @Schema(description = "结束时间")
    private String createTimeEnd;

}