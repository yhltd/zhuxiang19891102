package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.ProductMatter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 */
@Service
public interface IProductMatterService extends IService<ProductMatter> {

    /**
     * 批量添加
     * @param productMatterList
     * @return
     */
    boolean add(List<ProductMatter> productMatterList);

    /**
     * 批量修改
     * @param productMatterList
     * @return
     */
    boolean update(Integer productInfoId,List<ProductMatter> productMatterList);
}
