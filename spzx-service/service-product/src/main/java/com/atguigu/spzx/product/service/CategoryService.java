package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguitu.spzx.product.service
 * @className: CategoryService
 * @author: XiaoHB
 * @date: 2024/2/20 17:04
 */
public interface CategoryService {
    List<Category> selectOneCategory();

    List<Category> findCategoryTree();
}
