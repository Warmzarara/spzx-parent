package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductDetailsMapper;
import com.atguigu.spzx.manager.mapper.ProductMapper;
import com.atguigu.spzx.manager.mapper.ProductSkuMapper;
import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.service.impl
 * @className: ProductServiceImpl
 * @author: XiaoHB
 * @date: 2024/2/2 17:25
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page,limit);
        List<Product> list = productMapper.findByPage(productDto);
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo.getSize());
        return pageInfo;
    }

    /**
     * 1.保存商品基本信息至product表
     * 2.获取商品sku信息，保存sku信息，product_sku表
     * 3.保存商品详情到product_details表
     * @param product
     */
    @Override
    public void save(Product product) {
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.save(product);
        
        List<ProductSku> productSkuList = product.getProductSkuList();
        Integer i = 0;
        for(ProductSku e:productSkuList){
            e.setSkuCode(product.getId()+"_"+i);
            e.setProductId(e.getProductId());
            e.setSkuName(product.getName()+e.getSkuSpec());
            e.setSaleNum(0);
            e.setStatus(0);
            i++;
            productSkuMapper.save(e);
        }

        ProductDetails productDetails = new ProductDetails();
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetails.setProductId(product.getId());
        productDetailsMapper.save(productDetails);
    }

    @Override
    public Product getById(Long id) {
        Product product = productMapper.findProductById(id);
        
        List<ProductSku> list = productSkuMapper.findProductSkuByProductId(id);
        product.setProductSkuList(list);
        
        ProductDetails productDetails = productDetailsMapper.findProductDetailsByProuctId(id);
        String imageUrls = productDetails.getImageUrls();
        product.setDetailsImageUrls(imageUrls);

        return product;
    }

    @Override
    public void updateById(Product product) {
        productMapper.updateById(product);

        List<ProductSku> productSkuList = product.getProductSkuList();
        // TODO: 2024/2/2 遍历集合加箭头函数，回头看一下 
        productSkuList.forEach(e -> {
            productSkuMapper.updateById(e);
        });
        
        String detailsImageUrls = product.getDetailsImageUrls();
        ProductDetails productDetails = productDetailsMapper.findProductDetailsByProuctId(product.getId());
        productDetails.setImageUrls(detailsImageUrls);
        productDetailsMapper.updateById(productDetails);
    }

    @Override
    public void deleteById(Long id) {
         productMapper.deleteById(id);
         productSkuMapper.deleteByProductId(id);
         productDetailsMapper.deleteByProductId(id);
    }

    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        if(auditStatus == 1) {
            product.setAuditStatus(1);
            product.setAuditMessage("审批通过");
        } else {
            product.setAuditStatus(-1);
            product.setAuditMessage("审批不通过");
        }
        productMapper.updateById(product);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if(status == 1) {
            product.setStatus(1);
        } else {
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }
}
