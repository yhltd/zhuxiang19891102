package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.WorkOrderDetail;
import com.zx.pro.entity.WorkOrderInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 派工单信息汇总接口
 *
 * @author wanghui
 */
@Service
public interface IWorkOrderInfoService extends IService<WorkOrderInfo> {
    /**
     * 录入
     * @return 实体类
     */
    WorkOrderInfo add();

    /**
     * 查询派工单信息
     * @return 派工单信息list
     */
    List<WorkOrderInfo> getList();

    /**
     * 查询派工单信息
     * @param workOrder 根据派工单单号查询
     * @return 派工单信息list
     */
    List<WorkOrderInfo> getList(String workOrder);

    /**
     * 查询派工单信息
     * @param startTime,endTime 根据时间区间查询
     * @return 派工单信息list
     */
    List<WorkOrderInfo> getList(LocalDateTime startTime, LocalDateTime endTime);










}
