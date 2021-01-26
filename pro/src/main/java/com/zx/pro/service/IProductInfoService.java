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
     * 查询所有信息
     * @return
     */
    List<ProductInfo> getList();

    /**
     * 条件查询
     * @param orderId 订单号
     * @param productName 产品名称
     * @return
     */
    List<ProductInfo> getList(String orderId,String productName);

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

    /**
     * 修改
     * @param productInfo 产品信息
     * @return
     */
    boolean update(ProductInfo productInfo);

    /**
     * 批量删除
     * @param idList id集合
     * @return
     */
    boolean delete(List<Integer> idList);
}
