package com.zx.pro.controller;

import com.zx.pro.entity.WorkOrderInfo;
import com.zx.pro.service.IWorkOrderInfoService;
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

//    /**
//     * 录入
//     *
//     * @param map
//     * @return ResultInfo
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @Transactional
//    public ResultInfo add(@RequestBody HashMap map) {
//        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
//        try {
//            WorkOrderInfo workOrderInfo=GsonUtil.toEntity(gsonUtil.get("workOrderInfo"), WorkOrderInfo.class);
//            List<WorkOrderDetail> list = GsonUtil.toList(gsonUtil.get("workOrderDetailList"),WorkOrderDetail.class);
//
//            workOrderInfo=iWorkOrderInfoService.add(workOrderInfo,list);
//            return ResultInfo.success("新增成功", workOrderInfo);
////            if (StringUtils.isNotNull(workOrderInfo)) {
////                return ResultInfo.success("新增成功", workOrderInfo);
////            } else {
////                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
////                return ResultInfo.success("未新增", null);
////            }
//        }catch (Exception e){
//            e.printStackTrace();
//            //手动回滚
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            log.error("新增失败：{}", e.getMessage());
//            log.error("参数：{}", map);
//            return ResultInfo.error("错误");
//        }
//    }

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
