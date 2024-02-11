package com.atguigu.spzx.model.entity.order;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.entity.order
 * @className: OrderStatistics
 * @author: XiaoHB
 * @date: 2024/2/5 17:56
 */
@Data
public class OrderStatistics extends BaseEntity {

    private Date orderDate;     // 订单日期
    private BigDecimal totalAmount;     // 交易金额
    private Integer totalNum;       // 订单数

}
