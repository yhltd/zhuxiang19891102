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
     *
     * @return 实体类
     */
    WorkOrderInfo add();

    /**
     * 查询派工单信息
     *
     * @return 派工单信息list
     */
    List<WorkOrderInfo> getList();

    /**
     * 条件查询
     * @param workOrder 派工单号
     * @param startDateStr 开始日期
     * @param endDateStr 结束日期
     * @return
     */
    List<WorkOrderInfo> selectList(String workOrder, String startDateStr, String endDateStr);

    /**
     * 查询派工单信息
     *
     * @param state 状态
     * @return
     */
    List<WorkOrderInfo> getListByState(String state);

    /**
     * 修改状态
     *
     * @param workOrder
     * @param state
     * @return
     */
    boolean updateState(String workOrder, String state);


}
