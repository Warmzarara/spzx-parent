package com.atguigu.spzx.manager.test;


import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.test
 * @className: EasyExcelTeset
 * @author: XiaoHB
 * @date: 2024/1/29 18:12
 */
public class EasyExcelTest {
    public static void main(String[] args) {
        read();
        write();
    }
    public static void read(){
        //1.定义excel文件位置
        String fileName = "C://Users//admin//Desktop//01.xlsx";
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener();
        EasyExcel.read(fileName, CategoryExcelVo.class,excelListener)
                .sheet().doRead();
        List<CategoryExcelVo> data = excelListener.getData();
        System.out.println(data);
    }
    public static void write(){
        List<CategoryExcelVo> list = new ArrayList<>();
        list.add(new CategoryExcelVo(1L , "数码办公" , "",0L, 1, 1)) ;
        list.add(new CategoryExcelVo(11L , "华为手机" , "",1L, 1, 2)) ;
        EasyExcel.write("C://Users//admin//Desktop//02.xlsx",CategoryExcelVo.class)
                .sheet("分类数据").doWrite(list);
        
    }
}
