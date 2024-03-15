package com.atguigu.spzx.model.vo.product;

import com.alibaba.fastjson.JSONArray;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductSku;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.vo.product
 * @className: ProductItemVo
 * @author: XiaoHB
 * @date: 2024/2/27 16:48
 */
@Data
@Schema(description = "商品详情对象")
public class ProductItemVo {

    @Schema(description = "商品sku信息")
    private ProductSku productSku;

    @Schema(description = "商品信息")
    private Product product;

    @Schema(description = "商品轮播图列表")
    private List<String> sliderUrlList;

    @Schema(description = "商品详情图片列表")
    private List<String> detailsImageUrlList;

    @Schema(description = "商品规格信息")
    private JSONArray specValueList;

    @Schema(description = "商品规格对应商品skuId信息")
    private Map<String,Object> skuSpecValueMap;

}
// TODO: 2024/2/27 拦截器把swagger的页面也给拦截了，最后调试一下 
