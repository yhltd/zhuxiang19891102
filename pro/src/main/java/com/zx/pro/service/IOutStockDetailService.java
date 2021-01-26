package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.OutStockDetail;
import com.zx.pro.entity.SetStockDetail;
import com.zx.pro.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 */
@Service
public interface IOutStockDetailService extends IService<OutStockDetail> {

    /**
     * 查询所有入库明细
     * @return
     */
    List<OutStockDetail> getList();

    /**
     * 条件查询
     * @param setOrder 入库单号
     * @param productName 产品名称
     * @return
     */
    List<OutStockDetail> getList(String setOrder, String productName);

    /**
     * 根据订单号查询所有可出库产品
     * @param orderId 订单号
     * @return
     */
    List<OutStockDetail> getList(String orderId);

    /**
     * 批量添加
     * @param userInfo session中的用户信息
     * @param outStockDetails
     * @return
     */
    boolean add(UserInfo userInfo, List<OutStockDetail> outStockDetails);

    /**
     * 修改
     * @param setStockDetail
     * @return
     */
    boolean update(OutStockDetail setStockDetail);

    /**
     * 批量删除
     * @param idList
     * @return
     */
    boolean delete(List<Integer> idList);
}
