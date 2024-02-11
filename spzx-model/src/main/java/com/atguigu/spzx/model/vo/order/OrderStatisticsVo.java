package com.atguigu.spzx.model.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.vo.order
 * @className: OrderStatisticsVo
 * @author: XiaoHB
 * @date: 2024/2/5 19:18
 */
@Data
@Schema(description = "统计结果实体类")
public class OrderStatisticsVo {
    @Schema(description = "日期数据集合")
    private List<String> dateList ;
    
    @Schema(description = "总金额数据集合")
    private List<BigDecimal> amountList ;
}
