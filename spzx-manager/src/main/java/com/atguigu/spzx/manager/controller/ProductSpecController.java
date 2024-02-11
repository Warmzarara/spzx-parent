package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.controller
 * @className: ProductSpecController
 * @author: XiaoHB
 * @date: 2024/2/2 16:32
 */
@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {
    @Autowired
    private ProductSpecService productSpecService;
    
    @GetMapping("/findAll")
    public Result findAll(){
        List<ProductSpec> list = productSpecService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
    
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page,@PathVariable Integer limit){
        PageInfo<ProductSpec> pageInfo = productSpecService.findByPage(page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
    
    @PostMapping("/save")
    public Result save(@RequestBody ProductSpec productSpec){
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    
    @PutMapping("/updateById")
    public Result updateById(@RequestBody ProductSpec productSpec){
        productSpecService.updateById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        productSpecService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
