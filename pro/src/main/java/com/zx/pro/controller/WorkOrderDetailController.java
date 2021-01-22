package com.zx.pro.controller;

import com.zx.pro.entity.WorkOrderDetail;
import com.zx.pro.entity.WorkOrderDetailItem;
import com.zx.pro.service.IWorkOrderDetailService;
import com.zx.pro.service.IWorkOrderInfoService;
import com.zx.pro.util.DecodeUtil;
import com.zx.pro.util.GsonUtil;
import com.zx.pro.util.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author wanghui
 * @date 2021/1/14 10:23
 */

@Slf4j
@RestController
@RequestMapping("/work_order_detail")
public class WorkOrderDetailController {
    @Autowired
    private IWorkOrderDetailService iWorkOrderDetailService;
    @Autowired
    private IWorkOrderInfoService iWorkOrderInfoService;

    /**
     * 批量录入派工单
     *
     * @param //workOrderDetailList 录入派工单的集合对象
     * @return ResultInfo
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResultInfo add(@RequestBody HashMap map) {
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        //WorkOrderDetail workOrderDetail=new WorkOrderDetail();
        try {
            //WorkOrderInfo workOrderInfo=iWorkOrderInfoService.add();

            List<WorkOrderDetail> list = GsonUtil.toList(gsonUtil.get("workOrderDetailList"),WorkOrderDetail.class);
            if (iWorkOrderDetailService.add(list)) {
                return ResultInfo.success("录入成功", list);
            } else {
                return ResultInfo.success("未录入", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("录入失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误");
        }
    }

    /**
     * 查询派工单明细
     *
     * @return ResultInfo
     */
    @PostMapping("/getList")
    public ResultInfo getList() {
        List<WorkOrderDetailItem> getList = null;
        try {
            getList = iWorkOrderDetailService.getList();
            return ResultInfo.success("查询成功", getList);
        } catch (Exception e) {
            log.error("获取派工单信息集合失败：{}，参数：[workOrderInfoId: {}]", e.getMessage(), getList);
            return ResultInfo.error("错误");
        }
    }

    /**
     * 修改派工单明细
     *
     * @param workOrderDetailJson 修改过的派工单明细表对象
     * @return ResultInfo
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultInfo update(@RequestBody String workOrderDetailJson) {
        WorkOrderDetail workOrderDetail = null;
        try {
            workOrderDetail = DecodeUtil.decodeToJson(workOrderDetailJson, WorkOrderDetail.class);
            if (iWorkOrderDetailService.update(workOrderDetail)) {
                return ResultInfo.success("修改成功", workOrderDetail);
            } else {
                return ResultInfo.error("错误");
            }
        } catch (Exception e) {
            log.error("修改派工单失败：{}，参数：[workOrderDetail: {}]", e.getMessage(), workOrderDetail);
            return ResultInfo.error("错误");
        }
    }

    /**
     * 删除派工单明细
     *
     * @param map 根据id集合删除
     * @return ResultInfo
     */
    @PostMapping("/delete")
    public ResultInfo delete(@RequestBody HashMap map) {
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));

        List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"), Integer.class);

        try {
            if (iWorkOrderDetailService.delete(idList)) {
                return ResultInfo.success("删除成功", idList);
            } else {
                return ResultInfo.success("删除失败", idList);
            }
        } catch (Exception e) {
            log.error("删除派工单信息失败：{}，参数：[id: {}]", e.getMessage(), idList);
            return ResultInfo.error("错误");
        }
    }

    /**
     * 查询派工单明细
     *
     * @param workOrder 根据派工单单号查询
     * @return ResultInfo
     */
    @PostMapping("/getListByWorkOrder")
    public ResultInfo getListByWorkOrder(String workOrder) {
        List<WorkOrderDetailItem> getListByWorkOrder = null;
        try {
            getListByWorkOrder = iWorkOrderDetailService.getList(workOrder);
            return ResultInfo.success("查询成功", getListByWorkOrder);
        } catch (Exception e) {
            log.error("获取派工单信息集合失败：{}", e.getMessage(), getListByWorkOrder);
            return ResultInfo.error("错误");
        }
    }
}
