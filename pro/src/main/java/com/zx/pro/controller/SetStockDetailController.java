package com.zx.pro.controller;

import com.zx.pro.entity.SetStockDetail;
import com.zx.pro.entity.UserInfo;
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
@RequestMapping("/set_stock")
public class SetStockDetailController {

    @Autowired
    private ISetStockDetailService iSetStockDetailService;

    @RequestMapping("/post_list")
    public ResultInfo postList(){
        try {
            List<SetStockDetail> list = iSetStockDetailService.getList();
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
            String setOrder = map.get("setOrder").toString();
            String productName = map.get("productName").toString();

            List<SetStockDetail> list = iSetStockDetailService.getList(setOrder,productName);

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
            String workOrder = map.get("workOrder").toString();
            List<SetStockDetail> setStockDetailList =
                    GsonUtil.toList(gsonUtil.get("list"),SetStockDetail.class);
            UserInfo userInfo = GsonUtil.toEntity(SessionUtil.getToken(session),UserInfo.class);

            if (iSetStockDetailService.add(workOrder,userInfo,setStockDetailList)) {
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
    public ResultInfo update(@RequestBody String setStockDetailJson){
        try {
            SetStockDetail setStockDetail =
                    DecodeUtil.decodeToJson(setStockDetailJson,SetStockDetail.class,"createTime");
            if (iSetStockDetailService.update(setStockDetail)) {
                return ResultInfo.success("修改成功", null);
            } else {
                return ResultInfo.success("未修改", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改失败：{}", e.getMessage());
            log.error("参数：{}", setStockDetailJson);
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/delete")
    public ResultInfo delete(@RequestBody HashMap map){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"),Integer.class);
            if (iSetStockDetailService.delete(idList)) {
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
