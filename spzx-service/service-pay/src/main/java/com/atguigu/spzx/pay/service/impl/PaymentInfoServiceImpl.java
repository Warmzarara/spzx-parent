package com.atguigu.spzx.pay.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.feign.order.OrderFeignClient;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.pay.mapper.PaymentInfoMapper;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.pay.service.impl
 * @className: PaymentInfoServiceImpl
 * @author: XiaoHB
 * @date: 2024/3/15 18:17
 */
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Autowired
    private PaymentInfoMapper paymentInfoMapper;
    
    @Autowired
    private OrderFeignClient orderFeignClient;

    /**
     * 1.根据 orderNO 查询支付记录
     * 2.判断支付记录是否存在，如果存在直接返回，如果不存在则远程调用Order模块，根据orderNo获取订单信息，封装paymentInfo对象，最后保存进数据库。
     * @param orderNo
     * @return
     */
    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(orderNo);
        
        if (ObjectUtil.isNull(paymentInfo)) {
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String content = "";
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content += item.getSkuName() + " ";
            }
            paymentInfo.setContent(content);
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            paymentInfoMapper.save(paymentInfo);
            }
        return paymentInfo;
        }

    @Override
    public void updatePaymentStatus(Map<String, String> map, int i) {
        // 查询PaymentInfo
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(map.get("out_trade_no"));
        if (paymentInfo.getPaymentStatus() == 1) {
            return;
        }

        //更新支付信息
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setOutTradeNo(map.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(map));
        paymentInfoMapper.updateById(paymentInfo);
        
        //更新订单状态
        orderFeignClient.updateOrderStatusPayed(paymentInfo.getOrderNo());
    }

}
