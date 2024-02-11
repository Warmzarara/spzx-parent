package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.listener.ExcelListener;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.service.impl
 * @className: CategoryServiceImpl
 * @author: XiaoHB
 * @date: 2024/1/28 18:23
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 1.得到集合
     * 2.判断是否有下层分类（hasChildren）
     * @param id
     * @return
     */
    @Override
    public List<Category> findCategoryList(Long id) {
        //hasChildren:true有下层数据 false没有下层数据
        //1.根据id查询出list
        //2.遍历集合，判断是否有下层分类，有下层hasChildren为true
        List<Category> catetoryList = categoryMapper.selectCategoryByParentId(id);
        if(!CollectionUtil.isEmpty(catetoryList)){
            catetoryList.forEach(item ->{
                Integer count = categoryMapper.selectCountByParentId(item.getId());
                if(count>0){
                    item.setHasChildren(true);
                }else {
                    item.setHasChildren(false);
                }
            });
        }
        return catetoryList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try{
            //1.设置响应头和其他信息
            // 设置响应结果类型 
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //2.查询所有分类返回list
            List<Category> list = categoryMapper.findAll();
            
            //List<Category>-->List<CategoryExcelVo>
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>();
            for (Category category:list) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                // TODO: 2024/2/2 拷贝，回来理解一下 
                BeanUtils.copyProperties(category,categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);
            }
            
            //3.调用easyExcel的write方法
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet().doWrite(categoryExcelVoList);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

        
    }

    @Override
    public void importData(MultipartFile file) {
        //监听器
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener(categoryMapper);
        try{
            EasyExcel.read(file.getInputStream(),CategoryExcelVo.class,excelListener)
                    .sheet().doRead();    
        }catch (IOException e){
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        
    }
}
