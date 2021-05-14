package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.WorkOrderInfo;
import com.zx.pro.mapper.WorkOrderInfoMapper;
import com.zx.pro.service.IWorkOrderDetailService;
import com.zx.pro.service.IWorkOrderInfoService;
import com.zx.pro.util.OrderUtil;
import com.zx.pro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
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
    public WorkOrderInfo add(String workOder) {
        WorkOrderInfo workOrderInfo = new WorkOrderInfo();
        workOrderInfo.setCreateTime(LocalDateTime.now());
        workOrderInfo.setWorkOrder(workOder);
        workOrderInfo.setState("0");
        return this.save(workOrderInfo) ? workOrderInfo : null;
    }

    @Override
    public List<WorkOrderInfo> getList() {
        return this.list();
    }

    @Override
    public List<WorkOrderInfo> selectList(String workOrder, String startDateStr, String endDateStr) {
        LocalDateTime startDate = startDateStr != "" ?
                LocalDateTime.parse(startDateStr) :
                StringUtils.MIN_DATETIME;

        LocalDateTime endDate = endDateStr != "" ?
                LocalDateTime.parse(endDateStr) :
                StringUtils.MAX_DATETIME;

        QueryWrapper<WorkOrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("create_time", startDate, endDate);
        queryWrapper.like("work_order", workOrder);
        return this.list(queryWrapper);
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
}
