package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.ProductMatter;
import com.zx.pro.mapper.ProductMatterMapper;
import com.zx.pro.service.IProductMatterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public boolean update(Integer productInfoId, List<ProductMatter> productMatterList) {
        for(ProductMatter productMatter : productMatterList){
            //插入产品信息表id
            productMatter.setProductInfoId(productInfoId);
            //条件构造器
            UpdateWrapper<ProductMatter> updateWrapper = new UpdateWrapper<>();
            //product_info_id = #{productId}
            updateWrapper.eq("product_info_id",productInfoId);
            //matter_project_id = #{matterProjectId}
            updateWrapper.eq("matter_project_id",productMatter.getMatterProjectId());
            //修改
            if(!this.update(productMatter,updateWrapper)){
                return false;
            }
        }
        return true;
    }
}
