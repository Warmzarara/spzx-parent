package com.atguigu.spzx.order.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.feign.cart.CartFeignClient;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.feign.product.UserFeignClient;
import com.atguigu.spzx.model.dto.order.OrderInfoDto;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.order.OrderLog;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.mapper.OrderInfoMapper;
import com.atguigu.spzx.order.mapper.OrderItemMapper;
import com.atguigu.spzx.order.mapper.OrderLogMapper;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.atguigu.spzx.utils.AuthContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.order.service.Impl
 * @className: OrderInfoServiceImpl
 * @author: XiaoHB
 * @date: 2024/3/10 15:15
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    
    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;
    
    @Autowired
    private UserFeignClient userFeignClient;
    
    @Autowired
    private OrderItemMapper orderItemMapper;
    
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    
    @Autowired
    private OrderLogMapper orderLogMapper;
    

    @Override
    public TradeVo getTrade() {
        List<OrderItem> orderItemList = new ArrayList<>();

        List<CartInfo> cartInfoList = cartFeignClient.getAllCheckCart();
        cartInfoList.stream().forEach(cartInfo -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        });
        BigDecimal totalAmount = new BigDecimal(0);
        

        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        
        TradeVo tradeVo = new TradeVo();
        tradeVo.setOrderItemList(orderItemList);
        tradeVo.setTotalAmount(totalAmount);
        return tradeVo;    
    }

    /**
     * 生成订单
     * 1.从dto中获取所有的订单项，若订单项为空则抛出异常
     * 2.远程调用service-product的getBySkuId(skuId)方法，获取SKU信息，如果下单数量大于库存则抛出异常（库存不足）
     * 3.封装订单信息(OrderInfo)
     * 4.添加订单项(OrderItem)
     * @param orderInfoDto
     * @return
     */
    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        /*
         1.从dto中获取所有的订单项，若订单项为空则抛出异常
         */
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        if (CollectionUtil.isEmpty(orderItemList)) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        
        /*
        2.远程调用service-product的getBySkuId(skuId)方法，获取SKU信息，如果下单数量大于库存则抛出异常（库存不足）
         */
        for (OrderItem orderItem : orderItemList) {
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
            if (ObjectUtil.isNull(productSku)) {
                throw new GuiguException(ResultCodeEnum.DATA_ERROR);
            }
            if (orderItem.getSkuNum().intValue() > productSku.getStockNum()) {
                throw new GuiguException(ResultCodeEnum.STOCK_LESS);
            }
        }
        
        /*
        3.封装订单信息
         */
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        OrderInfo orderInfo = new OrderInfo();
        //订单编号
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        //用户收货地址信息
        UserAddress userAddress = userFeignClient.getAddressById(orderInfoDto.getUserAddressId());
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        orderInfoMapper.save(orderInfo);
        
        /*
        封装订单项
         */
        // TODO: 2024/3/12 可能会出错？ 出错的话换成强化for循环
        orderItemList.stream().forEach(orderItem -> {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        });
        
        /*
        封装记录表
         */
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);
        
        /*
        将生成订单的商品从购物车中删除
         */
        cartFeignClient.deleteChecked();
        
        /*
        返回订单id
         */
        return orderInfo.getId();
    }

    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return orderInfoMapper.getById(orderId); 
    }

    /**
     * 立即购买
     * @param skuId
     * @return
     */
    @Override
    public TradeVo buy(Long skuId) {
        // 查询商品
        ProductSku productSku = productFeignClient.getBySkuId(skuId);
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItemList.add(orderItem);

        // 计算总金额
        BigDecimal totalAmount = productSku.getSalePrice();
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);

        // 返回
        return tradeVo;
    }

    /**
     * 查询订单列表
     * @param page
     * @param limit
     * @param orderStatus
     * @return
     */
    @Override
    public PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        PageHelper.startPage(page,limit);
        List<OrderInfo> orderInfoList = orderInfoMapper.findUserPage(orderStatus,userId);
        orderInfoList.stream().forEach(orderInfo -> {
            List<OrderItem> orderItemList = orderItemMapper.findByOrderId(orderInfo.getId());
            orderInfo.setOrderItemList(orderItemList);
        });
        return new PageInfo<>(orderInfoList);
    }

    /**
     * 获取订单信息
     * 为订单信息封装订单项
     * @param orderNo
     * @return
     */
    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.getOrderInfoByOrderNo(orderNo);
        List<OrderItem> orderItemList = orderItemMapper.findByOrderId(orderInfo.getId());
        orderInfo.setOrderItemList(orderItemList);
        return orderInfo;
    }

    @Override
    public void updateOrderStatus(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.getOrderInfoByOrderNo(orderNo);
        orderInfo.setOrderStatus(1);
        orderInfo.setPaymentTime(new Date());
        orderInfo.setPayType(2);
        
        orderInfoMapper.updateById(orderInfo);
    }
}
