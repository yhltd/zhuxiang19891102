package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.WorkOrderInfo;
import com.zx.pro.mapper.WorkOrderInfoMapper;
import com.zx.pro.service.IWorkOrderInfoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 派工单接口实现类
 *
 * @author wanghui
 */
@Service
public class WorkOrderInfoImpl extends ServiceImpl<WorkOrderInfoMapper, WorkOrderInfo> implements IWorkOrderInfoService {

    @Override
    public boolean add(List<WorkOrderInfo> workOrderInfoList) {
        return saveBatch(workOrderInfoList);
    }

    @Override
    public WorkOrderInfo add(WorkOrderInfo workOrderInfo) {
        this.save(workOrderInfo);
        return workOrderInfo;
    }

    @Override
    public List<WorkOrderInfo> getList() {
        return this.list();
    }

    @Override
    public List<WorkOrderInfo> getList(String workOrder) {
        QueryWrapper<WorkOrderInfo> wrapper=new QueryWrapper<>();
        wrapper.like("work_order",workOrder);
        return this.list(wrapper);
    }

    @Override
    public List<WorkOrderInfo> getList(LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper<WorkOrderInfo> wrapper=new QueryWrapper<>();
        wrapper.between("create_time",startTime,endTime);
        return this.list(wrapper);
    }
}
