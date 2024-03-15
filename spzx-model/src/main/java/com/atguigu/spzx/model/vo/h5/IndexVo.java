package com.atguigu.spzx.model.vo.h5;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import lombok.Data;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.vo.h5
 * @className: IndexVo
 * @author: XiaoHB
 * @date: 2024/2/20 17:11
 */
@Data
public class IndexVo {
    private List<Category> categoryList;
    private List<ProductSku> productSkuList;
}
