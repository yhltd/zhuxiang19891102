package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.WorkOrderInfo;
import com.zx.pro.mapper.WorkOrderInfoMapper;
import com.zx.pro.service.IWorkOrderDetailService;
import com.zx.pro.service.IWorkOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 派工单接口实现类
 *
 * @author wanghui
 */
@Service
public class WorkOrderInfoImpl extends ServiceImpl<WorkOrderInfoMapper, WorkOrderInfo> implements IWorkOrderInfoService {

    @Autowired
    private IWorkOrderDetailService iWorkOrderDetailService;

    @Autowired
    private WorkOrderInfoMapper workOrderInfoMapper;

    @Override
    public WorkOrderInfo add() {
        WorkOrderInfo workOrderInfo = new WorkOrderInfo();
        workOrderInfo.setCreateTime(LocalDateTime.now());
        String workOrder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).replace("-", "").replace(":", "");
        workOrderInfo.setWorkOrder("P" + workOrder);
        workOrderInfo.setState("未开始");
        return this.save(workOrderInfo) ? workOrderInfo : null;
    }

    @Override
    public List<WorkOrderInfo> getList() {
        return this.list();
    }

    @Override
    public List<WorkOrderInfo> getListByState(String state) {
        QueryWrapper<WorkOrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", state);
        return this.list(queryWrapper);
    }

    @Override
    public boolean updateState(String workOrder, String state) {
        return workOrderInfoMapper.updateState(workOrder, state) > 0;
    }

    @Override
    public List<WorkOrderInfo> getList(String workOrder) {
        QueryWrapper<WorkOrderInfo> wrapper = new QueryWrapper<>();
        wrapper.like("work_order", workOrder);
        return this.list(wrapper);
    }

    @Override
    public List<WorkOrderInfo> getList(LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper<WorkOrderInfo> wrapper = new QueryWrapper<>();
        wrapper.between("create_time", startTime, endTime);
        return this.list(wrapper);
    }
}
