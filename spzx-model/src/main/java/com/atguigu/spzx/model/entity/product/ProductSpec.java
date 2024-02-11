package com.atguigu.spzx.model.entity.product;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.Data;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.entity.product
 * @className: ProductSpec
 * @author: XiaoHB
 * @date: 2024/2/2 16:31
 */
@Data
public class ProductSpec extends BaseEntity {

    private String specName;   // 规格名称
    private String specValue;  // 规格值

}