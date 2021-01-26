package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.SetStockDetail;
import com.zx.pro.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 */
@Service
public interface ISetStockDetailService extends IService<SetStockDetail> {

    /**
     * 查询所有入库明细
     * @return
     */
    List<SetStockDetail> getList();

    /**
     * 条件查询
     * @param setOrder 入库单号
     * @param productName 产品名称
     * @return
     */
    List<SetStockDetail> getList(String setOrder,String productName);

    /**
     * 批量添加
     * @param workOrder 派工单号
     * @param userInfo session中的用户信息
     * @param setStockDetailList
     * @return
     */
    boolean add(String workOrder,UserInfo userInfo,List<SetStockDetail> setStockDetailList);

    /**
     * 修改
     * @param setStockDetail
     * @return
     */
    boolean update(SetStockDetail setStockDetail);

    /**
     * 批量删除
     * @param idList
     * @return
     */
    boolean delete(List<Integer> idList);
}
