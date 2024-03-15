package com.atguigu.spzx.model.dto.order;

import com.atguigu.spzx.model.entity.order.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.dto.order
 * @className: OrderInfoDto
 * @author: XiaoHB
 * @date: 2024/3/12 16:41
 */
@Data
public class OrderInfoDto {

    //送货地址id
    private Long userAddressId;
    //运费
    private BigDecimal feightFee;
    //备注
    private String remark;
    //订单明细
    private List<OrderItem> orderItemList;

}