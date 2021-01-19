package com.zx.pro.controller;

import com.zx.pro.entity.WorkOrderDetail;
import com.zx.pro.entity.WorkOrderDetailItem;
import com.zx.pro.service.IWorkOrderDetailService;
import com.zx.pro.util.GsonUtil;
import com.zx.pro.util.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 批量录入派工单
     *
     * @param workOrderDetailList 录入派工单的集合对象
     * @return ResultInfo
     */
    @PostMapping("/addList")
    public ResultInfo add(List<WorkOrderDetail> workOrderDetailList) {
        try {
            if (iWorkOrderDetailService.add(workOrderDetailList)) {
                return ResultInfo.success("添加成功", workOrderDetailList);
            } else {
                return ResultInfo.success("未添加", workOrderDetailList);
            }
        } catch (Exception e) {
            log.error("获取派工单信息集合失败：{}，参数：[workOrderDetailList: {}]", e.getMessage(), workOrderDetailList);
            return ResultInfo.error("错误");
        }
    }

    /**
     * 录入派工单
     *
     * @param workOrderDetail 派工单明细类
     * @return ResultInfo
     */
    @PostMapping("/add")
    public ResultInfo add(WorkOrderDetail workOrderDetail) {
        try {
            workOrderDetail = iWorkOrderDetailService.add(workOrderDetail);
            return ResultInfo.success("添加成功", workOrderDetail);
        } catch (Exception e) {
            log.error("获取派工单信息集合失败：{}，参数：[workOrderDetail: {}]", e.getMessage(), workOrderDetail);
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
            return ResultInfo.success("添加成功", getList);
        } catch (Exception e) {
            log.error("获取派工单信息集合失败：{}，参数：[workOrderInfoId: {}]", e.getMessage(), getList);
            return ResultInfo.error("错误");
        }
    }

    /**
     * 修改派工单明细
     *
     * @param workOrderDetail 修改过的派工单明细表对象
     * @return ResultInfo
     */
    @PostMapping("/update")
    public ResultInfo update(WorkOrderDetail workOrderDetail) {
        try {
            if (iWorkOrderDetailService.update(workOrderDetail)) {
                return ResultInfo.success("添加成功", workOrderDetail);
            } else {
                return ResultInfo.error("错误");
            }
        } catch (Exception e) {
            log.error("获取派工单信息集合失败：{}，参数：[workOrderDetail: {}]", e.getMessage(), workOrderDetail);
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

        List<Integer> idList = GsonUtil.toEntity(gsonUtil.get("idList"),List.class);

        try {
            if (iWorkOrderDetailService.delete(idList)) {
                return ResultInfo.success("删除成功", idList);
            } else {
                return ResultInfo.success("删除失败", idList);
            }
        } catch (Exception e) {
            log.error("获取派工单信息集合失败：{}，参数：[id: {}]", e.getMessage(), idList);
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
            log.error("获取派工单信息集合失败：{}，参数：[workOrderInfoId: {}]", e.getMessage(), getListByWorkOrder);
            return ResultInfo.error("错误");
        }
    }
}
