package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductUnitMapper;
import com.atguigu.spzx.manager.service.ProductUnitService;
import com.atguigu.spzx.model.entity.product.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.service.impl
 * @className: ProductUnitServiceImpl
 * @author: XiaoHB
 * @date: 2024/2/2 17:55
 */
@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Autowired
    private ProductUnitMapper productUnitMapper;

    @Override
    public List<ProductUnit> findAll() {
        List<ProductUnit> list = productUnitMapper.findAll();
        return list;
    }
}
