package com.zx.pro.controller;

import com.zx.pro.entity.ProductInfo;
import com.zx.pro.entity.WorkOrderDetail;
import com.zx.pro.entity.WorkOrderDetailItem;
import com.zx.pro.service.IProductInfoService;
import com.zx.pro.service.IWorkOrderDetailService;
import com.zx.pro.service.IWorkOrderInfoService;
import com.zx.pro.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
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
    @Autowired
    private IProductInfoService iProductInfoService;

    /**
     * 批量录入派工单
     *
     * @param //workOrderDetailList 录入派工单的集合对象
     * @return ResultInfo
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResultInfo add(@RequestBody HashMap map, HttpSession session) {
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isAdd("派工单")) {
                return ResultInfo.error(401, "无权限");
            }

            List<WorkOrderDetail> list = GsonUtil.toList(gsonUtil.get("workOrderDetailList"), WorkOrderDetail.class);
            log.error("参数：{}", map);
            if (iWorkOrderDetailService.add(list)) {
                return ResultInfo.success("录入成功", list);
            } else {
                return ResultInfo.success("录入失败", list);
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
    public ResultInfo getList(HttpSession session) {
        List<WorkOrderDetailItem> getList = null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("派工单")) {
                return ResultInfo.error(401, "无权限");
            }

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
    public ResultInfo update(@RequestBody String workOrderDetailJson, HttpSession session) {
        WorkOrderDetail workOrderDetail = null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isUpdate("派工单")) {
                return ResultInfo.error(401, "无权限");
            }

            workOrderDetail = DecodeUtil.decodeToJson(workOrderDetailJson, WorkOrderDetail.class);
            if (iWorkOrderDetailService.update(workOrderDetail)) {
                return ResultInfo.success("修改成功", workOrderDetail);
            } else {
                return ResultInfo.error("修改失败");
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
    public ResultInfo delete(@RequestBody HashMap map, HttpSession session) {
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));

        List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"), Integer.class);

        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isDelete("派工单")) {
                return ResultInfo.error(401, "无权限");
            }

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
    public ResultInfo getListByWorkOrder(String workOrder, HttpSession session) {
        List<WorkOrderDetailItem> getListByWorkOrder = null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("派工单")) {
                return ResultInfo.error(401, "无权限");
            }

            getListByWorkOrder = iWorkOrderDetailService.getList(workOrder);
            return ResultInfo.success("查询成功", getListByWorkOrder);
        } catch (Exception e) {
            log.error("获取派工单信息集合失败：{}", e.getMessage(), getListByWorkOrder);
            return ResultInfo.error("错误");
        }
    }

    /**
     * 查询车间生产信息集合
     *
     * @return
     */
    @PostMapping("/getListByWorkShop")
    public ResultInfo getListByWorkShop(HttpSession session) {
        List<WorkOrderDetailItem> getListByWorkShop = null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("车间生产汇总")) {
                return ResultInfo.error(401, "无权限");
            }

            getListByWorkShop = iWorkOrderDetailService.getProductionListByWorkShop();
            return ResultInfo.success("查询成功", getListByWorkShop);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            log.error("参数：{}", getListByWorkShop);
            return ResultInfo.error("错误");
        }
    }

    /**
     * 查询产线生产信息集合
     *
     * @return
     */
    @PostMapping("/getListByWorkLine")
    public ResultInfo getListByWorkLine(HttpSession session) {
        List<WorkOrderDetailItem> getListByWorkLine = null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("产线生产汇总")) {
                return ResultInfo.error(401, "无权限");
            }

            getListByWorkLine = iWorkOrderDetailService.getProductionListByWorkLine();
            return ResultInfo.success("查询成功", getListByWorkLine);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            log.error("参数：{}", getListByWorkLine);
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/getWorkShopListDate")
    public ResultInfo getWorkShopListDate(@RequestBody HashMap map, HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("车间生产汇总")) {
                return ResultInfo.error(401, "无权限");
            }

            String startDate = map.get("startDate").toString();
            String endDate = map.get("endDate").toString();


            List<WorkOrderDetailItem> list =
                    iWorkOrderDetailService.getWorkShopByWorkDate(startDate, endDate);

            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("查询成功", list);
            } else {
                return ResultInfo.success("查询成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/getWorkLineListDate")
    public ResultInfo getWorkLineListDate(@RequestBody HashMap map, HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("产线生产汇总")) {
                return ResultInfo.error(401, "无权限");
            }

            String startDate = map.get("startDate").toString();
            String endDate = map.get("endDate").toString();

            List<WorkOrderDetailItem> list =
                    iWorkOrderDetailService.getWorkLineByWorkDate(startDate, endDate);
            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("查询成功", list);
            } else {
                return ResultInfo.success("查询成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/getProductList")
    public ResultInfo getProductList() {
        List<ProductInfo> getList = null;
        try {
            getList = iProductInfoService.getList();
            return ResultInfo.success("查询成功", getList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            log.error("参数：{}", getList);
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/get_min")
    public ResultInfo getMin(HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isAdd("派工单")){
                return ResultInfo.error(401,"无权限");
            }

            Date date = iWorkOrderDetailService.getMinDetailByWorkDate();
            if (StringUtils.isNotNull(date)) {
                return ResultInfo.success(date);
            } else {
                return ResultInfo.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            return ResultInfo.error("错误");
        }
    }
}
