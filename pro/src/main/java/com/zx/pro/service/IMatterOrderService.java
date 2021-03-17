package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterOrder;
import com.zx.pro.entity.MatterOrderItem;
import com.zx.pro.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMatterOrderService extends IService<MatterOrder> {

    /**
     * 查询全部明细
     * @return
     */
    List<MatterOrderItem> postList();

    /**
     * 条件查询明细
     * @param orderId 订单号
     * @param code 物料代码
     * @return
     */
    List<MatterOrderItem> selectList(String orderId, String code);

    /**
     * 修改
     * @param uid
     * @param oldNum 原数量
     * @param newNum 新数量
     * @return
     */
    boolean update(UserInfo user,int id, String orderId, int uid, int oldNum, int newNum);

    /**
     * 添加
     * @param id
     * @return
     */
    boolean add(int id);

    /**
     * 批量添加
     * @param list
     * @return
     */
    boolean add(List<MatterOrder> list);

    /**
     * 删除
     * @param idList
     * @return
     */
    boolean delete(List<Integer> idList);

    /**
     * 批量删除
     * @param orderId 订单号
     * @return
     */
    boolean deleteByOrderId(String orderId);
}
