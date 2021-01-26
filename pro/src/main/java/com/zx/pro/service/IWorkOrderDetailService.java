package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.WorkOrderDetail;
import com.zx.pro.entity.WorkOrderDetailItem;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 派工单明细接口
 *
 * @author wanghui
 */
@Service
public interface IWorkOrderDetailService extends IService<WorkOrderDetail> {

    /**
     * 添加
     * @param workOrderDetail 添加物料订单表对象
     * @return 是否添加成功
     */
    WorkOrderDetail add(WorkOrderDetail workOrderDetail);

    /**
     * 批量添加
     * @param list
     * @return
     */
    boolean add(List<WorkOrderDetail> list);

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

    /**
     * 查询
     * @return 车间生产信息集合
     */
    List<WorkOrderDetailItem>getProductionListByWorkShop();

    /**
     * 查询
     * @return 产线生产信息集合
     */
    List<WorkOrderDetailItem>getProductionListByWorkLine();

    /**
     * 根据时间范围查询
     * @return 车间生产信息集合
     */
    List<WorkOrderDetailItem>getWorkShopByWorkDate(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 根据时间范围查询
     * @return 产线生产信息集合
     */
    List<WorkOrderDetailItem>getWorkLineByWorkDate(LocalDateTime startDate,LocalDateTime endDate);
}
