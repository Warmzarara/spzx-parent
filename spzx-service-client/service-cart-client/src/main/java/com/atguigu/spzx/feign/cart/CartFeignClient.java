package com.atguigu.spzx.feign.cart;

import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "service-cart")

public interface CartFeignClient {
    @GetMapping(value = "/api/order/cart/auth/getAllChecked")
    public List<CartInfo> getAllCheckCart();

    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    Result deleteChecked() ;
}
