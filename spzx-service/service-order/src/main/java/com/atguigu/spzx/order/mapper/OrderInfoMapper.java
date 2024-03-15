package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.order.mapper
 * @className: OrderInfoMapper
 * @author: XiaoHB
 * @date: 2024/3/10 15:17
 */
@Mapper
public interface OrderInfoMapper {
    void save(OrderInfo orderInfo);

    OrderInfo getById(Long orderId);

    List<OrderInfo> findUserPage(Integer orderStatus, Long userId);

    OrderInfo getOrderInfoByOrderNo(String orderNo);

    void updateById(OrderInfo orderInfo);
}
