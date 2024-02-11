package com.atguigu.spzx.manager.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.test
 * @className: ExcelListener
 * @author: XiaoHB
 * @date: 2024/1/29 18:13
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {
    private List<T> data = new ArrayList<>();
    //读取excel内容
    //从第二行开始读取(第一行是表头)，把每行读取内容封装到容器t
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        data.add(t);
    }
    
    public List<T> getData(){
        return data;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        List<T> list = new ArrayList();
        
//        list.add("a");
    }
}
