package com.atguigu.spzx.model.vo.h5;

import com.atguigu.spzx.model.entity.order.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.vo.h5
 * @className: TradeVo
 * @author: XiaoHB
 * @date: 2024/3/10 16:16
 */
@Data
@Schema(description = "结算实体类")
public class TradeVo {

    @Schema(description = "结算总金额")
    private BigDecimal totalAmount;

    @Schema(description = "结算商品列表")
    private List<OrderItem> orderItemList;

}