package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    void save(ProductSku e);

    List<ProductSku> findProductSkuByProductId(Long id);

    void updateById(ProductSku e);

    void deleteByProductId(Long id);
    

}
