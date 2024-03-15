package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.controller
 * @className: ProductController
 * @author: XiaoHB
 * @date: 2024/2/2 17:20
 */
@RestController
@RequestMapping(value="/admin/product/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 列表方法
     * @param page
     * @param limit
     * @param productDto
     * @return
     */
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page, @PathVariable Integer limit, ProductDto productDto){
        PageInfo<Product> pageInfo = productService.findByPage(page,limit,productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 新增商品功能
     * @param product
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Product product){
        productService.save(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据id查询商品详情
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id){
        Product product = productService.getById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    /**
     * 提交修改
     */
    @PutMapping("/updateById")
    public Result updateById(@RequestBody Product product){
        productService.updateById(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 审核接口
     * @param id
     * @param auditStatus
     * @return
     */
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id,@PathVariable Integer auditStatus){
        productService.updateAuditStatus(id,auditStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 上下架状态
     * @param id
     * @param status
     * @return
     */
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,@PathVariable Integer status){
        productService.updateStatus(id,status);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
