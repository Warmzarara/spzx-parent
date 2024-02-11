package com.atguigu.spzx.model.entity.product;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.Data;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.entity.product
 * @className: CategoryBrand
 * @author: XiaoHB
 * @date: 2024/2/1 19:31
 */
@Data
public class CategoryBrand extends BaseEntity {

    private Long brandId;
    private Long categoryId;

    // 扩展的属性用来封装前端所需要的数据
    private String categoryName;
    private String brandName;
    private String logo;

}