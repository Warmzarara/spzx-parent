package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.CategoryBrandMapper;
import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.service.impl
 * @className: CategoryBrandServiceImpl
 * @author: XiaoHB
 * @date: 2024/2/1 19:27
 */
@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page,limit);
        List<CategoryBrand> list = categoryBrandMapper.findByPage(categoryBrandDto);
        PageInfo<CategoryBrand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save();
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        List<Brand> list = categoryBrandMapper.findBrandByCategoryId(categoryId);
        return list;
    }

}
