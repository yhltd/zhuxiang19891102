package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.ProductInfo;
import com.zx.pro.mapper.ProductInfoMapper;
import com.zx.pro.service.IProductInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements IProductInfoService {

    @Override
    public boolean add(ProductInfo productInfo) {
        return this.save(productInfo);
    }

    @Override
    public boolean add(List<ProductInfo> productInfoList) {
        return this.saveBatch(productInfoList,50);
    }

    @Override
    public List<ProductInfo> getList() {
        return this.list();
    }

}
