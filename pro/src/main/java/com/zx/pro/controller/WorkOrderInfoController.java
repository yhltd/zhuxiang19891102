package com.zx.pro.controller;

import com.zx.pro.entity.WorkOrderInfo;
import com.zx.pro.service.IWorkOrderInfoService;
import com.zx.pro.util.PowerUtil;
import com.zx.pro.util.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
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
     * 查询派工单信息
     *
     * @return ResultInfo
     */
    @PostMapping("/getList")
    public ResultInfo workOrderList(HttpSession session) {
        List<WorkOrderInfo> getList = null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("派工单汇总")){
                return ResultInfo.error(401,"无权限");
            }

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
    public ResultInfo workOrderListByWorkOrder(@RequestBody String workOrder,HttpSession session) {
        List<WorkOrderInfo> getList = null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("派工单汇总")){
                return ResultInfo.error(401,"无权限");
            }

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
    public ResultInfo workOrderListByCreatTime(@RequestBody HashMap map, HttpSession session) {
        LocalDateTime startTime = LocalDateTime.parse(map.get("startTime").toString());
        LocalDateTime endTime = LocalDateTime.parse(map.get("endTime").toString());
        List<WorkOrderInfo> getList = null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("派工单汇总")){
                return ResultInfo.error(401,"无权限");
            }

            getList = iWorkOrderInfoService.getList(startTime, endTime);
            return ResultInfo.success("查询成功", getList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("通过创建时间获取派工单信息集合失败{}，参数：[getList: {}]", e.getMessage(), getList);
            return ResultInfo.error("错误!");
        }
    }
}
