package com.zx.pro.controller;

import com.zx.pro.entity.*;
import com.zx.pro.service.IOrderInfoService;
import com.zx.pro.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author dai
 */
@Slf4j
@RestController
@RequestMapping("/order_info")
public class OrderInfoController {

    @Autowired
    private IOrderInfoService iOrderInfoService;

    @RequestMapping("/post_list")
    public Object postList(HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("订单汇总")){
                return ResultInfo.error(401,"无权限");
            }

            List<OrderInfoItem> list = iOrderInfoService.getList();

            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("查询成功", list);
            } else {
                return ResultInfo.success("查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            return ResultInfo.error("查询失败");
        }
    }

    @RequestMapping("/select_list")
    public ResultInfo postList(HttpSession session,@RequestBody HashMap map) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("订单汇总")){
                return ResultInfo.error(401,"无权限");
            }

            String projectName = map.get("projectName").toString();
            String orderId = map.get("orderId").toString();
            String startDate = map.get("startDate").toString();
            String endDate = map.get("endDate").toString();

            List<OrderInfoItem> list = iOrderInfoService.getList(projectName, orderId, startDate, endDate);

            return ResultInfo.success("查询成功", list);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            return ResultInfo.error("查询失败");
        }
    }

    @RequestMapping("/post_state_list")
    public ResultInfo postStateList(HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("订单状态")){
                return ResultInfo.error(401,"无权限");
            }

            List<OrderState> list = iOrderInfoService.getOrderStateList("");

            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("查询成功", list);
            } else {
                return ResultInfo.success("查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            return ResultInfo.error("查询失败");
        }
    }

    @RequestMapping("/select_state_list")
    public ResultInfo postStateList(HttpSession session,@RequestBody HashMap map) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("订单状态")){
                return ResultInfo.error(401,"无权限");
            }

            String orderId = map.get("orderId").toString();

            List<OrderState> list = iOrderInfoService.getOrderStateList(orderId);

            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("查询成功", list);
            } else {
                return ResultInfo.success("查询失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            return ResultInfo.error("查询失败");
        }
    }

    @RequestMapping("/update")
    public ResultInfo update(HttpSession session,@RequestBody String orderInfoItemJson){
        try{
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isUpdate("订单汇总")){
                return ResultInfo.error(401,"无权限");
            }

            OrderInfoItem orderInfoItem = DecodeUtil.decodeToJson(orderInfoItemJson,
                    OrderInfoItem.class,"createTime");

            if(iOrderInfoService.update(orderInfoItem)){
                return ResultInfo.success("修改成功",orderInfoItem);
            }else{
                return ResultInfo.success("未修改");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("修改失败：{}",e.getMessage());
            log.error("参数：{}",orderInfoItemJson);
            return ResultInfo.error("修改失败");
        }
    }

    @RequestMapping("/delete")
    @Transactional
    public ResultInfo delete(HttpSession session,@RequestBody HashMap map){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));

        try{
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isDelete("订单汇总")){
                return ResultInfo.error(401,"无权限");
            }

            List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"),Integer.class);

            if(iOrderInfoService.delete(idList)){
                return ResultInfo.success("删除成功");
            }else{
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultInfo.success("未删除");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除失败：{}",e.getMessage());
            log.error("参数：{}",map);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultInfo.error("删除失败");
        }
    }

    @RequestMapping("/add")
    @Transactional
    public ResultInfo add(HttpSession session,@RequestBody HashMap map){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));

        try{
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isAdd("订单汇总")){
                return ResultInfo.error(401,"无权限");
            }

            int projectId = Integer.parseInt(map.get("projectId").toString());
            String orderId=map.get("orderId").toString();
            String comment = map.get("comment").toString();
            List<MatterOrder> matterOrderList = GsonUtil.toList(gsonUtil.get("matterOrderList"),MatterOrder.class);

            OrderInfo orderInfo = iOrderInfoService.add(projectId,orderId, comment, matterOrderList);
            if(StringUtils.isNotNull(orderInfo)){
                return ResultInfo.success("新增成功", orderInfo);
            }else{
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultInfo.success("未新增");
            }

        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("新增失败：{}",e.getMessage());
            log.error("参数：{}",map);
            return ResultInfo.error("新增失败");
        }
    }
}