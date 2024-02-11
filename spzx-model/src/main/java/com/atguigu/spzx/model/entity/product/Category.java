package com.atguigu.spzx.model.entity.product;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.entity.product
 * @className: category
 * @author: XiaoHB
 * @date: 2024/1/28 18:29
 */
@Data
public class Category extends BaseEntity {
    private String name;
    private String imageUrl;
    private Long parentId;
    private Integer status;
    private Integer orderNum;

    private Boolean hasChildren;

    private List<Category> children;
}
