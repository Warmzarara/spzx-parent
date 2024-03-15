package com.atguigu.spzx.cart.service;

import com.atguigu.spzx.model.entity.h5.CartInfo;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.cart.service
 * @className: CartService
 * @author: XiaoHB
 * @date: 2024/3/9 18:55
 */
public interface CartService {
    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> getCartList();
    
    void deleteCart(Long skuId);

    void checkCart(Long skuId, Integer isChecked);

    void allCheckCart(Integer isChecked);
    
    void clearCart();
    
    //远程调用
    List<CartInfo> getAllChecked();

    void deleteChecked();
}
