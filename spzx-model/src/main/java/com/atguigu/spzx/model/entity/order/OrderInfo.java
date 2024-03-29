package com.atguigu.spzx.model.entity.order;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.entity.order
 * @className: OrderInfo
 * @author: XiaoHB
 * @date: 2024/2/5 18:00
 */
// com.atguigu.spzx.model.entity.order;
@Data
@Schema(description = "OrderInfo")
public class OrderInfo extends BaseEntity {

    private Long userId;
    private String nickName;
    private String orderNo;
    private Long couponId;
    private BigDecimal totalAmount;
    private BigDecimal couponAmount;
    private BigDecimal originalTotalAmount;
    private BigDecimal feightFee;
    private Integer payType;
    private Integer orderStatus;
    private String receiverName;
    private String receiverPhone;
    private String receiverTagName;
    private String receiverProvince;
    private String receiverCity;
    private String receiverDistrict;
    private String receiverAddress;
    private Date paymentTime;
    private Date deliveryTime;
    private Date receiveTime;
    private String remark;
    private Date cancelTime;
    private String cancelReason;

    @Schema(description = "订单项列表")
    private List<OrderItem> orderItemList;

}