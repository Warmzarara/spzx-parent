package com.atguigu.spzx.product.service.impl;

import cn.hutool.Hutool;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @projectName: spzx-parent
 * @package: com.atguitu.spzx.product.service.impl
 * @className: CategoryServiceImpl
 * @author: XiaoHB
 * @date: 2024/2/20 17:04
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    /**
     * 查询redis是否包含所有的一级分类
     * 是 则直接返回
     * 否 则查询数据库，将最新的结果放到redis
     */
    public List<Category> selectOneCategory() {
        String categoryOneJSON = redisTemplate.opsForValue().get("category:one");
        
        if(StringUtils.hasText(categoryOneJSON)){
            List<Category> exitCategoryList = JSON.parseArray(categoryOneJSON, Category.class);
            System.out.println("-----------------------------------FUCKU--------------------------------");
            return exitCategoryList;
        }
        List<Category> categoryList = categoryMapper.selectOneCategory();
        redisTemplate.opsForValue().set(
                "category:one"
                ,JSON.toJSONString(categoryList)
                , 7
                , TimeUnit.DAYS);
        return categoryList;
    }

    /**
     * 1.查找所有的分类
     * 2.查找父节点为0的分类
     * @return
     */
    @Cacheable(value = "category",key = "'all'")
    @Override
    public List<Category> findCategoryTree() {
        
        List<Category> allCategoryList = categoryMapper.findALl();
        List<Category> oneCategoryList = allCategoryList.stream()    // TODO: 2024/2/26 stream的使用 
                .filter(item -> item.getParentId().longValue() == 0)  //筛选出父id为0的节点
                .collect(Collectors.toList());//封装进新的列表中
        
        oneCategoryList.stream().forEach(oneCategory->{
            List<Category> twoCategoryList = allCategoryList.stream()
                    .filter(item -> item.getParentId() == oneCategory.getId())
                    .collect(Collectors.toList());
            oneCategory.setChildren(twoCategoryList);
            
            twoCategoryList.stream().forEach(twoCategory->{
                List<Category> threeCatetoryList = allCategoryList.stream()
                        .filter(item -> item.getParentId() == twoCategory.getId())
                        .collect(Collectors.toList());
                twoCategory.setChildren(threeCatetoryList);
            });
        });
        return oneCategoryList;
    }
    
    
}
