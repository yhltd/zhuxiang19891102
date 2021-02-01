package com.zx.pro.controller;

import com.zx.pro.entity.WorkOrderInfo;
import com.zx.pro.service.IWorkOrderInfoService;
import com.zx.pro.util.GsonUtil;
import com.zx.pro.util.PowerUtil;
import com.zx.pro.util.ResultInfo;
import com.zx.pro.util.StringUtils;
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
            if (!powerUtil.isSelect("派工单")) {
                return ResultInfo.error(401, "无权限");
            }

            getList = iWorkOrderInfoService.getList();
            return ResultInfo.success("查询成功", getList);
        } catch (Exception e) {
            log.error("获取派工单信息集合失败：{}，参数：[]", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }


    @PostMapping("/getListByState")
    public ResultInfo getListByState() {
        try {
            List<WorkOrderInfo> list = iWorkOrderInfoService.getListByState("0");

            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("查询成功", list);
            } else {
                return ResultInfo.success("查询失败");
            }
        } catch (Exception e) {
            log.error("获取派工单信息集合失败：{}", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }

    @PostMapping("/selectList")
    public ResultInfo workOrderListByWorkOrder(HttpSession session, @RequestBody HashMap map) {
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("派工单")) {
                return ResultInfo.error(401, "无权限");
            }

            String workOrder = gsonUtil.get("workOrder");
            String startDate = gsonUtil.get("startDate");
            String endDate = gsonUtil.get("endDate");

            List<WorkOrderInfo> list = iWorkOrderInfoService.selectList(workOrder, startDate, endDate);
            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("查询成功", list);
            } else {
                return ResultInfo.success("查询成功");
            }

        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误!");
        }
    }
}
