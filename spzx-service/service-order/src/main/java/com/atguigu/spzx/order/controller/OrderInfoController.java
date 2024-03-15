package com.atguigu.spzx.order.controller;

import com.atguigu.spzx.model.dto.order.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.order.controller
 * @className: OrderInfoController
 * @author: XiaoHB
 * @date: 2024/3/10 15:00
 */
@Tag(name = "订单管理")
@RestController
@RequestMapping("api/order/orderInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
//@CrossOrigin
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Operation(summary = "确认下单")
    @GetMapping("auth/trade")
    public Result<TradeVo> trade() {
        TradeVo tradeVo = orderInfoService.getTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "提交订单")
    @PostMapping("auth/submitOrder")
    public Result<Long> submitOrder(@Parameter(name = "orderInfoDto", description = "请求参数实体类", required = true) @RequestBody OrderInfoDto orderInfoDto) {
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }
    
    @Operation(summary = "获取订单信息")
    @GetMapping("auth/{orderId}")
    public Result getOrderInfo(@Parameter(name = "orderId",description = "订单id",required = true) @PathVariable Long orderId){
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }
    
    @Operation(summary = "立即购买")
    @GetMapping("auth/buy/{skuId}")
    public Result buy(@PathVariable Long skuId){
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取订单分页列表")
    @GetMapping("auth/{page}/{limit}")
    public Result<PageInfo<OrderInfo>> list(
            @Parameter(name = "page", description = "当前页码", required = true)
            @PathVariable Integer page,

            @Parameter(name = "limit", description = "每页记录数", required = true)
            @PathVariable Integer limit,

            @Parameter(name = "orderStatus", description = "订单状态", required = false)
            @RequestParam(required = false, defaultValue = "") Integer orderStatus) {
        PageInfo<OrderInfo> pageInfo = orderInfoService.findUserPage(page, limit, orderStatus);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 远程调用，根据orderNo获取orderInfo
     * @param orderNo
     * @return
     */
    @Operation(summary = "获取订单信息")
    @GetMapping("auth/getOrderInfoByOrderNo/{orderNo}")
    public OrderInfo getOrderInfoByOrderNo(@PathVariable String orderNo){
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderNo(orderNo);
        return orderInfo;
    }
    
    @GetMapping("auth/updateOrderStatusPayed/{orderNo}")
    public Result updateOrderStatusPayed(@PathVariable(value = "orderNo") String orderNo){
        orderInfoService.updateOrderStatus(orderNo);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
