package com.atguigu.spzx.manager.listener;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.listener
 * @className: ExcelListener
 * @author: XiaoHB
 * @date: 2024/1/30 16:39
 */

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * easyExcel监听器不能交给spring管理
 */
public class ExcelListener<T> implements ReadListener<T> {
    /**
     * 构造方法传递mapper操作数据库
     */
    private CategoryMapper categoryMapper;
    public ExcelListener(CategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;
    }
    /**
     * 每100行数据操作一次数据库，然后清理list
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 从第二行开始读取，将读取内容封装进T
     * @param t
     * @param analysisContext
     */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        //每行数据对象t放入集合中
        cachedDataList.add(t);
        if(cachedDataList.size()>=BATCH_COUNT){
            //一次性将数据添加到数据库
            saveData();
            //清空缓存
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //保存100余数的行数的数据
        saveData();
    }

    /**
     * 保存的方法
     */
    private void saveData(){
        categoryMapper.batchInsert((List<CategoryExcelVo>)cachedDataList);
    }
}
