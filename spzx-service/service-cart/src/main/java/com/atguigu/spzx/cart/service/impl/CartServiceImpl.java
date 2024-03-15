package com.atguigu.spzx.cart.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.cart.service.impl
 * @className: CartServiceImpl
 * @author: XiaoHB
 * @date: 2024/3/9 18:55
 */
@Service
public class CartServiceImpl implements CartService {
    
    private String getCartKey(Long userId){
        //定义key：“user:cart:userId”
        return "user:cart:"+userId;
    }
    
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    
    @Autowired
    private ProductFeignClient client;
    /**
     * 1.必须是登录状态，获取当前登录用户id，作为redis的key值（hash类型）
     * 2.从threadLocal中取用户信息
     * 3.查redis，如果商品已经存在于redis中，更改数量，如果不存在则添加商品
     * @param skuId
     * @param skuNum
     */
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        CartInfo cartInfo = new CartInfo();
        if (ObjectUtil.isNotNull(cartInfoObj)) {
            cartInfo = JSON.parseObject(String.valueOf(cartInfoObj), CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            cartInfo.setIsChecked(1);   //选中状态
            cartInfo.setUpdateTime(new Date());
        } else {
            ProductSku productSku = client.getBySkuId(skuId);
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        System.out.println(cartKey);
        redisTemplate.opsForHash().put(cartKey,String.valueOf(skuId),JSON.toJSONString(cartInfo));
    }

    /**
     * 购物车列表接口
     * 1.从threadLocal中获取用户信息
     * 2.用userId从redis中获取购物车信息
     * 3.处理购物车信息封装成集合并返回
     * @return
     */
    @Override
    public List<CartInfo> getCartList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        
        String cartKey = this.getCartKey(userId);
        List<Object> valueList = redisTemplate.opsForHash().values(cartKey);
        System.out.println(valueList);
        
        if(CollectionUtil.isNotEmpty(valueList)){
            List<CartInfo> cartInfoList = valueList.stream()
                    .map(value -> JSON.parseObject(value.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
            return cartInfoList;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteCart(Long skuId) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        redisTemplate.opsForHash().delete(cartKey,String.valueOf(skuId));
    }

    @Override
    public void checkCart(Long skuId, Integer isChecked) {

        // 获取当前登录的用户数据
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        Boolean hasKey = redisTemplate.opsForHash().hasKey(cartKey, String.valueOf(skuId));
        if(hasKey) {
            String cartInfoJSON = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId)).toString();
            CartInfo cartInfo = JSON.parseObject(cartInfoJSON, CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put(cartKey , String.valueOf(skuId) , JSON.toJSONString(cartInfo));
        }

    }

    @Override
    public void allCheckCart(Integer isChecked) {

        // 获取当前登录的用户数据
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        // 获取所有的购物项数据
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        if(CollectionUtil.isNotEmpty(objectList)) {
            objectList.stream().map(cartInfoJSON -> {
                CartInfo cartInfo = JSON.parseObject(cartInfoJSON.toString(), CartInfo.class);
                cartInfo.setIsChecked(isChecked);
                return cartInfo ;
            }).forEach(cartInfo -> redisTemplate.opsForHash().put(cartKey , String.valueOf(cartInfo.getSkuId()) , JSON.toJSONString(cartInfo)));

        }
    }

    @Override
    public void clearCart() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        
        redisTemplate.delete(cartKey);
    }

    /**
     * 远程调用
     * @return
     */
    @Override
    public List<CartInfo> getAllChecked() {
        String cardKey = this.getCardKey();
        List<Object> values = redisTemplate.opsForHash().values(cardKey);
        if(CollectionUtil.isNotEmpty(values)){
            List<CartInfo> cartInfoList = 
                    values.stream()
                            .map(value -> JSON.parseObject(value.toString(), CartInfo.class))
                            .filter(cartInfo->cartInfo.getIsChecked()==1)
                            .collect(Collectors.toList());
            return cartInfoList;
        }
        return new ArrayList<>();
    }

    /**
     *远程调用，将已选商品从购物车删除
     */
    @Override
    public void deleteChecked() {
        String cardKey = this.getCardKey();
        List<Object> values = redisTemplate.opsForHash().values(cardKey);
        values.stream()
                .map(value -> JSON.parseObject(value.toString(),CartInfo.class))
                .filter(cartInfo -> cartInfo.getIsChecked()==1)
                .forEach(cartInfo -> {
                    redisTemplate.opsForHash().delete(cardKey,String.valueOf(cartInfo.getSkuId()));
                });
    }
    
    
    

    /**
     * 工具方法
     */
    private UserInfo getUserInfo(){
        return AuthContextUtil.getUserInfo();
    } 
    
    private String getCardKey(){
        return ("user:cart:" + this.getUserInfo().getId());
    }

}
