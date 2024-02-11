package com.atguigu.spzx.model.vo.product;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.vo.product
 * @className: CategoryExcelVo
 * @author: XiaoHB
 * @date: 2024/1/29 18:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 *  value:表头 index:位置，从0开始
 */
public class CategoryExcelVo {
    
    @ExcelProperty(value = "id",index = 0)
    private Long id;
    
    @ExcelProperty(value = "名称",index = 1)
    private String name;
    
    @ExcelProperty(value = "图片url",index = 2)
    private String imageUrl;
    
    @ExcelProperty(value = "上级id",index = 3)
    private Long parentId;
    
    @ExcelProperty(value = "状态",index = 4)
    private Integer status;
    
    @ExcelProperty(value = "排序",index = 5)
    private Integer orderNum;
}