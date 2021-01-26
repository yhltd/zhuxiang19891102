package com.zx.pro.controller;

import com.zx.pro.entity.OutStockDetail;
import com.zx.pro.entity.SetStockDetail;
import com.zx.pro.entity.UserInfo;
import com.zx.pro.service.IOutStockDetailService;
import com.zx.pro.service.ISetStockDetailService;
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
@RequestMapping("/out_stock")
public class OutStockController {

    @Autowired
    private IOutStockDetailService iOutStockDetailService;

    @RequestMapping("/post_list")
    public ResultInfo postList(){
        try {
            List<OutStockDetail> list = iOutStockDetailService.getList();
            if(StringUtils.isNotNull(list)){
                return ResultInfo.success("获取成功", list);
            }else{
                return ResultInfo.success("查询失败");
            }
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/select_list")
    public ResultInfo selectList(@RequestBody HashMap map){
        try {
            String setOrder = map.get("outOrder").toString();
            String productName = map.get("productName").toString();

            List<OutStockDetail> list = iOutStockDetailService.getList(setOrder,productName);

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

    @RequestMapping("/select_list_outStock")
    public ResultInfo selectListByOrderId(@RequestBody HashMap map){
        try {
            String orderId = map.get("orderId").toString();

            List<OutStockDetail> list = iOutStockDetailService.getList(orderId);

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

    @RequestMapping("/add")
    @Transactional
    public ResultInfo add(HttpSession session, @RequestBody HashMap map){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            List<OutStockDetail> outStockDetailList =
                    GsonUtil.toList(gsonUtil.get("list"),OutStockDetail.class);
            UserInfo userInfo = GsonUtil.toEntity(SessionUtil.getToken(session),UserInfo.class);

            if (iOutStockDetailService.add(userInfo,outStockDetailList)) {
                return ResultInfo.success("新增成功");
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultInfo.success("未新增", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("新增失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/update")
    public ResultInfo update(@RequestBody String outStockDetaillJson){
        try {
            OutStockDetail outStockDetail =
                    DecodeUtil.decodeToJson(outStockDetaillJson,OutStockDetail.class,"createTime");
            if (iOutStockDetailService.update(outStockDetail)) {
                return ResultInfo.success("修改成功", null);
            } else {
                return ResultInfo.success("未修改", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改失败：{}", e.getMessage());
            log.error("参数：{}", outStockDetaillJson);
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/delete")
    public ResultInfo delete(@RequestBody HashMap map){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"),Integer.class);
            if (iOutStockDetailService.delete(idList)) {
                return ResultInfo.success("删除成功", null);
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultInfo.success("未删除", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("删除失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误");
        }
    }
}
