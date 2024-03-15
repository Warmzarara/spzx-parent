package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.IndexVo;
import com.atguigu.spzx.product.service.CategoryService;
import com.atguigu.spzx.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguitu.spzx.product.controller
 * @className: IndexController
 * @author: XiaoHB
 * @date: 2024/2/20 17:02
 */
@Tag(name="首页接口管理")
@RestController
@RequestMapping(value="/api/product/index")
//@CrossOrigin
    
public class IndexController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    
    @GetMapping
    /**
     * 1.获取所有一级分类
     * 2.获取前十位畅销商品
     * 3.将数据封装进VO对象中
     */
    public Result Index(){
        List<Category> categoryList = categoryService.selectOneCategory();
        
        List<ProductSku> productSkuList = productService.selectProductSkuBySale();

        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }
}
