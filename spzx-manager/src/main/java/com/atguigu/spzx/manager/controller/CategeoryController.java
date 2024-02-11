package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.controller
 * @className: CatageoryController
 * @author: XiaoHB
 * @date: 2024/1/20 19:23
 */
@RestController
@RequestMapping(value = "/admin/product/category")
public class CategeoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/findCategoryList/{id}")
    public Result findCategoryList(@PathVariable Long id){
        List<Category> list =  categoryService.findCategoryList(id);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
    
    @GetMapping("/exportData")
    public Result exportData(HttpServletResponse response){   
        categoryService.exportData(response);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    
    @PostMapping("/importData")
    public Result importData(MultipartFile file){
        //1.获取上传文件
        categoryService.importData(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    
}
