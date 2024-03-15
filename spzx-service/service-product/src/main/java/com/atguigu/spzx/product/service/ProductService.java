package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.dto.product.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.product.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguitu.spzx.product.service
 * @className: ProductService
 * @author: XiaoHB
 * @date: 2024/2/20 17:04
 */
public interface ProductService {

    List<ProductSku> selectProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);
    
    ProductSku getBySkuId(Long skuId);
}
