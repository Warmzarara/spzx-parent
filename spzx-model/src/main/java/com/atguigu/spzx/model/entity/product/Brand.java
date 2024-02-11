package com.atguigu.spzx.model.entity.product;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.Data;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.entity.product
 * @className: Brand
 * @author: XiaoHB
 * @date: 2024/2/1 18:24
 */
@Data
public class Brand extends BaseEntity {

    private String name;
    private String logo;

}
