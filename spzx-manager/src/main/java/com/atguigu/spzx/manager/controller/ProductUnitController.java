package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductUnitService;
import com.atguigu.spzx.model.entity.product.ProductUnit;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.controller
 * @className: ProductUnionController
 * @author: XiaoHB
 * @date: 2024/2/2 17:48
 */
@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {
    @Autowired
    private ProductUnitService productUnitService;
    
    @GetMapping("/findAll")
    public Result findAll(){
        List<ProductUnit> list = productUnitService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
