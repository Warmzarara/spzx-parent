package com.atguigu.spzx.model.dto.product;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.Data;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.dto.product
 * @className: ProductDto
 * @author: XiaoHB
 * @date: 2024/2/2 17:21
 */
@Data
public class ProductDto extends BaseEntity {

    private Long brandId;
    private Long category1Id;
    private Long category2Id;
    private Long category3Id;

}