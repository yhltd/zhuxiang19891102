package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.WorkOrderDetail;
import com.zx.pro.entity.WorkOrderDetailItem;
import com.zx.pro.mapper.WorkOrderDetailMapper;
import com.zx.pro.service.IWorkOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean add(List<WorkOrderDetail> workOrderDetailList) {
        return saveBatch(workOrderDetailList);
    }

    @Override
    public WorkOrderDetail add(WorkOrderDetail workOrderDetail) {
        this.save(workOrderDetail);
        return workOrderDetail;
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

    @Override
    public boolean delete(List<Integer>id) {
        return removeByIds(id);
    }



}
