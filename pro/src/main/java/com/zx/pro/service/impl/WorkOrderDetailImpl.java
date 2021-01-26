package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.WorkOrderDetail;
import com.zx.pro.entity.WorkOrderDetailItem;
import com.zx.pro.entity.WorkOrderInfo;
import com.zx.pro.mapper.WorkOrderDetailMapper;
import com.zx.pro.service.IWorkOrderDetailService;
import com.zx.pro.service.IWorkOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 派工单明细接口实现类
 *
 * @author wanghui
 */
@Service
public class WorkOrderDetailImpl extends ServiceImpl<WorkOrderDetailMapper, WorkOrderDetail> implements IWorkOrderDetailService {

    @Autowired
    private WorkOrderDetailMapper workOrderDetailMapper;

    @Autowired
    private IWorkOrderInfoService iWorkOrderInfoService;


    @Override
    public WorkOrderDetail add(WorkOrderDetail workOrderDetail) {
        return this.save(workOrderDetail) ? workOrderDetail : null;
    }

    @Override
    public boolean add(List<WorkOrderDetail> list) {
        WorkOrderInfo workOrderInfo = iWorkOrderInfoService.add();
        list.forEach(workOrderDetail -> {
            workOrderDetail.setWorkOrderInfoId(workOrderInfo.getId());
        });
        //批量插入，插入批次50
        return this.saveBatch(list, 50);
    }

    @Override
    public List<WorkOrderDetailItem> getList() {
        return workOrderDetailMapper.getList();
    }

    @Override
    public List<WorkOrderDetailItem> getList(String workOrder) {
        return workOrderDetailMapper.getListByWorkOrder(workOrder);
    }

    @Override
    public boolean update(WorkOrderDetail workOrderDetail) {
        UpdateWrapper<WorkOrderDetail> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",workOrderDetail.getId());
        return this.update(workOrderDetail,updateWrapper);
    }

//    @Override
//    public boolean update(WorkOrderDetail workOrderDetail) {
//        return workOrderDetailMapper.update(workOrderDetail);
//    }

    @Override
    public boolean delete(List<Integer>id) {
        return removeByIds(id);
    }

    @Override
    public List<WorkOrderDetailItem> getProductionListByWorkShop() {
        return workOrderDetailMapper.getProductionListByWorkShop();
    }

    @Override
    public List<WorkOrderDetailItem> getProductionListByWorkLine() {
        return workOrderDetailMapper.getProductionListByWorkLine();
    }

    @Override
    public List<WorkOrderDetailItem> getWorkShopByWorkDate(LocalDateTime startDate, LocalDateTime endDate) {
        return workOrderDetailMapper.getWorkShopByWorkDate(startDate,endDate);
    }

    @Override
    public List<WorkOrderDetailItem> getWorkLineByWorkDate(LocalDateTime startDate, LocalDateTime endDate) {
        return workOrderDetailMapper.getWorkLineByWorkDate(startDate,endDate);
    }


}
