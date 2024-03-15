package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.product.controller
 * @className: BrandController
 * @author: XiaoHB
 * @date: 2024/2/26 17:25
 */
@RestController
@RequestMapping("/api/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    
    @Operation(summary = "获取全部品牌")
    @GetMapping("findAll")
    public Result findAll(){
        List<Brand> brandList = brandService.findAll();
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
    
}
