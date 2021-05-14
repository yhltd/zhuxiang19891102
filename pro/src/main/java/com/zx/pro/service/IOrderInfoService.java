package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterOrder;
import com.zx.pro.entity.OrderInfo;
import com.zx.pro.entity.OrderInfoItem;
import com.zx.pro.entity.OrderState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author dai
 */
@Service
public interface IOrderInfoService extends IService<OrderInfo> {

    List<OrderInfoItem> getList();

    List<OrderInfoItem> getList(String projectName,String orderId,String startDateStr,String endDateStr);

    List<OrderState> getOrderStateList();

    List<OrderState> getOrderStateList(String orderId);
    /**
     * 修改操作
     * @param orderInfoItem 订单信息的子类
     * @return 修改成功 子类 修改失败 null
     */
    boolean update(OrderInfoItem orderInfoItem);

    /**
     * 删除操作
     * @param idList id集合
     * @return true 删除成功 false 删除失败
     */
    boolean delete(List<Integer> idList);

    /**
     * 根据项目id删除
     * @param idList 项目id集合
     * @return
     */
    boolean deleteByProjectId(List<Integer> idList);

    /**
     * 新增订单
     * @param projectId 项目id
     * @param comment 贸易条款
     * @param matterOrderList 所用物料集合
     * @return
     */
    OrderInfo add(int projectId,String orderId, String comment, List<MatterOrder> matterOrderList);

    /**
     * 修改订单状态
     * @param orderInfoId 订单信息id
     * @param state 状态
     * @return
     */
    boolean updateState(Integer orderInfoId,String state);

    /**
     * 修改订单状态
     * @param orderId 订单号
     * @param state 状态
     * @return
     */
    boolean updateState(String orderId,String state);
}
