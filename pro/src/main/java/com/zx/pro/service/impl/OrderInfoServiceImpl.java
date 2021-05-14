package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.zx.pro.entity.*;
import com.zx.pro.mapper.OrderInfoMapper;
import com.zx.pro.service.IMatterOrderService;
import com.zx.pro.service.IOrderInfoService;
import com.zx.pro.util.OrderUtil;
import com.zx.pro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dai
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private IMatterOrderService matterOrderService;

    @Override
    public List<OrderInfoItem> getList() {
        return orderInfoMapper.postList();
    }

    @Override
    public List<OrderInfoItem> getList(String projectName, String orderId, String startDateStr, String endDateStr) {
        LocalDateTime startDate = startDateStr != "" ?
                LocalDateTime.parse(startDateStr) :
                StringUtils.MIN_DATETIME;

        LocalDateTime endDate = endDateStr != "" ?
                LocalDateTime.parse(endDateStr) :
                StringUtils.MAX_DATETIME;
        return orderInfoMapper.getList(projectName, orderId, startDate, endDate);
    }

    @Override
    public List<OrderState> getOrderStateList() {
        return orderInfoMapper.getOrderStateList();
    }

    @Override
    public List<OrderState> getOrderStateList(String orderId) {
        return orderInfoMapper.selectOrderStateList(orderId);
    }

    @Override
    public boolean update(OrderInfoItem orderInfoItem) {
        return this.updateById(StringUtils.cast(orderInfoItem));
    }

    @Override
    public boolean delete(List<Integer> idList) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("order_id");
        queryWrapper.in("id", idList);
        List<String> orderList = new ArrayList<>();
        for(OrderInfo orderInfo : this.list(queryWrapper)){
            orderList.add(orderInfo.getOrderId());
        }
        return matterOrderService.deleteByOrderList(orderList) ? this.removeByIds(idList) : false;
    }

    @Override
    public boolean deleteByProjectId(List<Integer> idList) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("project_info_id",idList);
        return this.remove(queryWrapper);
    }

    @Override
    public OrderInfo add(int projectId,String orderId, String comment, List<MatterOrder> matterOrderList) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderId);
        orderInfo.setCreateTime(LocalDateTime.now());
        orderInfo.setComment(comment);
        orderInfo.setProjectInfoId(projectId);
        if(this.save(orderInfo)){
            for(MatterOrder matterOrder : matterOrderList){
                matterOrder.setOrderId(orderInfo.getOrderId());
            }
            if(matterOrderService.add(matterOrderList)){
                return orderInfo;
            }
        }
        return null;
    }

    @Override
    public boolean updateState(Integer orderInfoId, String state) {
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", orderInfoId);
        updateWrapper.set("state", state);
        return this.update(updateWrapper);
    }

    @Override
    public boolean updateState(String orderId, String state) {
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id", orderId);
        updateWrapper.set("state", state);
        return this.update(updateWrapper);
    }
}
