package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.dto.product.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.product.ProductItemVo;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.product.controller
 * @className: ProductController
 * @author: XiaoHB
 * @date: 2024/2/26 17:27
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Operation(summary = "分页查询")
    @GetMapping("/{page}/{limit}")
    public Result findByPage(
            @Parameter(name = "page",description = "当前页码",required = true) 
            @PathVariable Integer page,
            @Parameter(name = "limit",description = "每页记录数",required = true)
            @PathVariable Integer limit,
            @Parameter(name = "productSkuDto",description = "搜索条件对象",required = false)
            ProductSkuDto productSkuDto
            ) {
        PageInfo<ProductSku> pageInfo = productService.findByPage(page,limit,productSkuDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
    
    @Operation(summary = "商品详情")
    @GetMapping("/item/{skuId}")
    public Result item(@PathVariable Long skuId){
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 远程调用,根据skuId查询sku详情
     * @param skuId
     * @return
     */
    @GetMapping("/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId){
        ProductSku productSku = productService.getBySkuId(skuId);
        return productSku;
    }
}
