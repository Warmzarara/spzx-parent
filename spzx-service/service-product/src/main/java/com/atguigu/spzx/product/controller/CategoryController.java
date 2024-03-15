package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.product.controller
 * @className: CategoryController
 * @author: XiaoHB
 * @date: 2024/2/25 21:22
 */
@RestController
@Tag(name = "菜单管理接口")
@RequestMapping("/api/product/category")
//@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("findCategoryTree")
    public Result findCategoryTree(){
        List<Category> categoryList = new ArrayList<>();
        categoryList = categoryService.findCategoryTree();
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }
}
