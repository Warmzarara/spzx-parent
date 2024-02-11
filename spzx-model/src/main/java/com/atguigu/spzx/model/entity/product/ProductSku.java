package com.atguigu.spzx.model.entity.product;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.entity.product
 * @className: ProductSku
 * @author: XiaoHB
 * @date: 2024/2/2 18:15
 */
// com.atguigu.spzx.model.entity.product;
@Data
public class ProductSku extends BaseEntity {

    private String skuCode;
    private String skuName;
    private Long productId;
    private String thumbImg;
    private BigDecimal salePrice;
    private BigDecimal marketPrice;
    private BigDecimal costPrice;
    private Integer stockNum;
    private Integer saleNum;
    private String skuSpec;
    private String weight;
    private String volume;
    private Integer status;

}
