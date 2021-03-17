package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.ProductInfo;
import com.zx.pro.mapper.ProductInfoMapper;
import com.zx.pro.service.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements IProductInfoService {
    @Override
    public List<ProductInfo> getList() {
        return this.list();
    }
    @Autowired
    ProductInfoMapper productInfoMapper;


    @Override
    public List<ProductInfo> getList(String orderId, String productName) {
        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("order_info_id",orderId);
        queryWrapper.like("product_name",productName);
        return this.list(queryWrapper);
    }

    @Override
    public boolean add(ProductInfo productInfo) {
        return this.save(productInfo);
    }

    @Override
    public boolean add(List<ProductInfo> productInfoList) {
        return this.saveBatch(productInfoList,50);
    }

    @Override
    public boolean update(ProductInfo productInfo) {
        return this.updateById(productInfo);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        return this.removeByIds(idList);
    }


    /**
     * 订单汇总表更改功能
     */

}
