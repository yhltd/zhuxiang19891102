package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.ProductInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 */
@Service
public interface IProductInfoService extends IService<ProductInfo> {

    /**
     * 新增产品
     * @param productInfo
     * @return
     */
    boolean add(ProductInfo productInfo);

    /**
     * 批量添加产品
     * @param productInfoList
     * @return
     */
    boolean add(List<ProductInfo> productInfoList);

    List<ProductInfo> getList();
}
