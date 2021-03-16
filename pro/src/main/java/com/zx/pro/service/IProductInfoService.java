package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterOrder;
import com.zx.pro.entity.Order_matter;
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


    boolean update(ProductInfo productInfo);

    /**
     * 批量删除
     * @param idList id集合
     * @return
     */
    boolean delete(List<Integer> idList);



    /**
     * 订单汇总表更改功能
     */


    /**
     * 查询所有信息
     * @return
     */
    List<Order_matter> getmoList();

    List<Order_matter> getmoonList(String orderId,String productName);

    int getid(String mattername);

    int updatematterorder(int metterid,int moid,int num);

    int deletemo(int id);

    String selectgteorderid(int id);

    int deleteorderid(int id);

    /**
     * 订单汇总表更改功能end
     */
}
