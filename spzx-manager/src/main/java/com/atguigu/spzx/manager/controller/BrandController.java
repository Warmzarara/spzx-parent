package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.enums.OperatorType;
import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.manager.service.impl.BrandServiceImpl;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.controller
 * @className: BrandController
 * @author: XiaoHB
 * @date: 2024/2/1 18:17
 */
@RestController
@RequestMapping("/admin/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    
    @GetMapping("/findAll")
    public Result findAll(){
        List<Brand> list = brandService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    /**
     * 列表方法
     * @param page
     * @param limit
     * @return
     */
    @Log(title = "品牌管理:品牌列表",businessType = 0,operatorType = OperatorType.OTHER)
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Integer page, @PathVariable Integer limit){
        PageInfo<Brand> pageInfo = brandService.findByPage(page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
    
    @PostMapping("/save")
    public Result save(@RequestBody Brand brand){
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // TODO: 2024/3/2 测试使用工具类获取Bean容器 
    public void getBean(HttpServletRequest request){
        RequestContextUtils.findWebApplicationContext(request).getBean("brandServiceImpl");
        WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean(BrandServiceImpl.class);
    }

    // TODO: 2024/2/1 修改、删除功能未编写
}
