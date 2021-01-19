package com.zx.pro.controller;

import com.zx.pro.entity.WorkOrderInfo;
import com.zx.pro.service.IWorkOrderInfoService;
import com.zx.pro.util.DecodeUtil;
import com.zx.pro.util.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author wanghui
 * @date 2021/1/14 9:59
 */

@Slf4j
@RestController
@RequestMapping("/work_order_info")
public class WorkOrderInfoController {
    @Autowired
    private IWorkOrderInfoService iWorkOrderInfoService;

    /**
     * 批量录入
     *
     * @param workOrderInfoList 录入集合对象
     * @return ResultInfo
     */
    @PostMapping("/addList")
    public ResultInfo addWorkOrderInfoList(List<WorkOrderInfo> workOrderInfoList) {
        try {
            if (iWorkOrderInfoService.add(workOrderInfoList)) {
                return ResultInfo.success("录入成功", workOrderInfoList);
            } else {
                return ResultInfo.success("录入失败", workOrderInfoList);
            }
        } catch (Exception e) {
            log.error("批量录入失败：{}，参数：[workOrderInfoList: {}]", e.getMessage(), workOrderInfoList);
            return ResultInfo.error("错误");
        }
    }

    /**
     * 录入
     *
     * @param workOrderInfoJson 返回录入对象
     * @return ResultInfo
     */
    @PostMapping("/add")
    public ResultInfo addWorkOrderInfo(@RequestBody String workOrderInfoJson) {
        WorkOrderInfo workOrderInfo = null;
        try {
            workOrderInfo = DecodeUtil.decodeToJson(workOrderInfoJson, WorkOrderInfo.class, "createTime");
            workOrderInfo = iWorkOrderInfoService.add(workOrderInfo);
            return ResultInfo.success("录入成功", workOrderInfo);
        } catch (Exception e) {
            log.error("录入失败：{}，参数：[workOrderInfo: {}]", e.getMessage(), workOrderInfo);
            return ResultInfo.error("错误");
        }
    }

    /**
     * 查询派工单信息
     *
     * @return ResultInfo
     */
    @PostMapping("/getList")
    public ResultInfo workOrderList() {
        List<WorkOrderInfo> getList = null;
        try {
            getList = iWorkOrderInfoService.getList();
            return ResultInfo.success("查询成功", getList);
        } catch (Exception e) {
            log.error("获取派工单信息集合失败：{}，参数：[]", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }

    /**
     * 查询派工单信息
     *
     * @param workOrder 根据派工单单号查询
     * @return ResultInfo
     */
    @PostMapping("/getListByWorkOrder")
    public ResultInfo workOrderListByWorkOrder(@RequestBody String workOrder) {
        List<WorkOrderInfo> getList = null;
        try {
            getList = iWorkOrderInfoService.getList(workOrder.split("=")[1]);
            return ResultInfo.success("查询成功", getList);
        } catch (Exception e) {
            log.error("通过派工单id获取派工单信息集合失败：{}，参数：[getList: {}]", e.getMessage(), getList);
            return ResultInfo.error("错误!");
        }
    }

    /**
     * 查询派工单信息
     *
     * @param map 根据创建时间查询
     * @return ResultInfo
     */
    @RequestMapping("/getListByCreateTime")
    public ResultInfo workOrderListByCreatTime(@RequestBody HashMap map) {
        LocalDateTime startTime = LocalDateTime.parse(map.get("startTime").toString());
        LocalDateTime endTime = LocalDateTime.parse(map.get("endTime").toString());

        List<WorkOrderInfo> getList = null;
        try {
            getList = iWorkOrderInfoService.getList(startTime, endTime);
            return ResultInfo.success("查询成功", getList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("通过创建时间获取派工单信息集合失败{}，参数：[getList: {}]", e.getMessage(), getList);
            return ResultInfo.error("错误!");
        }
    }
}
