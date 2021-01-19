package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.WorkOrderDetail;
import com.zx.pro.entity.WorkOrderDetailItem;
import com.zx.pro.mapper.WorkOrderDetailMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 派工单明细接口
 *
 * @author wanghui
 */
@Service
public interface IWorkOrderDetailService extends IService<WorkOrderDetail> {

    /**
     * 批量录入
     *
     * @param workOrderDetailList 订单明细集合
     * @return 是否添加成功
     */
    boolean add(List<WorkOrderDetail> workOrderDetailList);

    /**
     * 录入
     *
     * @param workOrderDetail 订单明细对象
     * @return 实体类
     */
    WorkOrderDetail add(WorkOrderDetail workOrderDetail);

    /**
     * 查询派工单明细
     * @return 派工单明细集合
     */
    List<WorkOrderDetailItem> getList();

    /**
     * 查询派工单明细
     *
     * @param workOrder 根据派工单单号查询
     * @return 派工单明细集合
     */
    List<WorkOrderDetailItem> getList(String workOrder);

    /**
     * 修改派工单明细
     *
     * @param workOrderDetail 修改过的派工单明细表对象
     * @return 是否修改成功
     */
    boolean update(WorkOrderDetail workOrderDetail);

    /**
     * 批量删除派工单明细
     *
     * @param id 根据id删除
     * @return 是否删除成功
     */
    boolean delete(List<Integer>id);





}
