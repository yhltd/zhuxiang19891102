package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.ProductMatter;
import com.zx.pro.mapper.ProductMatterMapper;
import com.zx.pro.service.IProductMatterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 */
@Service
public class ProductMatterServiceImpl extends ServiceImpl<ProductMatterMapper, ProductMatter> implements IProductMatterService {

    @Override
    public boolean add(List<ProductMatter> productMatterList) {
        return this.saveBatch(productMatterList,50);
    }
}
