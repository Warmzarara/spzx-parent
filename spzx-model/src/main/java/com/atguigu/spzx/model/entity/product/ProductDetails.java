package com.atguigu.spzx.model.entity.product;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.Data;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.entity.product
 * @className: ProductDetails
 * @author: XiaoHB
 * @date: 2024/2/2 18:28
 */
@Data
public class ProductDetails extends BaseEntity {

    private Long productId;
    private String imageUrls;

}