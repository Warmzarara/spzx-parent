package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.model.dto.product.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.product.ProductItemVo;
import com.atguigu.spzx.product.mapper.ProductDetailsMapper;
import com.atguigu.spzx.product.mapper.ProductMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @projectName: spzx-parent
 * @package: com.atguitu.spzx.product.service.impl
 * @className: ProductServiceImpl
 * @author: XiaoHB
 * @date: 2024/2/20 17:05
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductSkuMapper productSkuMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private ProductDetailsMapper productDetailsMapper;
    
    @Override
    public List<ProductSku> selectProductSkuBySale() {
        return productSkuMapper.selectProductSkuBySale();
    }

    /**
     * 分页列表方法
     * @param page
     * @param limit
     * @param productSkuDto
     * @return
     */
    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productSkuDto);
        PageInfo pageInfo = new PageInfo(productSkuList);
        return pageInfo;
    }

    /**
     * 商品详情
     * 1.根据skuId获取ProductSku
     * 2.从ProductSku获取ProductId并用其来获取Product
     * 3.再用ProductId获取ProductDetails
     * 4.封装skuSpecMap，key:skuSpec value:Id
     * @param skuId
     * @return
     */
    @Override
    public ProductItemVo item(Long skuId) {
        //当前sku信息
        ProductSku productSku = productSkuMapper.getById(skuId);
        //通过sku获取当前商品Id
        Long productId = productSku.getProductId();
        //获取当前商品
        Product product = productMapper.getById(productId);
        //获取当前商品详情
        ProductDetails productDetails = productDetailsMapper.getByProductId(productId);
        //商品详情图片
        List<String> imgList = Arrays.asList(productDetails.getImageUrls().split(","));
        //获取当前商品的轮播图
        List sliderList = Arrays.asList(product.getSliderUrls().split(","));
        //获取商品规格信息
        JSONArray specValues = JSONObject.parseArray(product.getSpecValue());
        List<ProductSku> productSkuList = productSkuMapper.findByProductId(productId);
        //遍历ProductSkuList，封装 商品规格对应skuId的map
        Map<String,Object> skuSpecValueMap = new HashMap<>();
        productSkuList.stream().forEach(sku -> {
            skuSpecValueMap.put(sku.getSkuSpec(),sku.getId());
        });
        ProductItemVo productItemVo = new ProductItemVo();
        
        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setDetailsImageUrlList(imgList);
        productItemVo.setSliderUrlList(sliderList);
        productItemVo.setSpecValueList(specValues);
        //ProductDetails的
//        product
        return productItemVo;
    }

    @Override
    public ProductSku getBySkuId(Long skuId) {
        ProductSku productSku = productSkuMapper.getById(skuId);
        return productSku;
    }
}
