package com.zx.pro.controller;

import com.zx.pro.entity.SetStockDetail;
import com.zx.pro.entity.Stock;
import com.zx.pro.entity.UserInfo;
import com.zx.pro.service.IStockService;
import com.zx.pro.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author dai
 */
@Slf4j
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private IStockService iStockService;

    @RequestMapping("/post_list")
    public ResultInfo postList(){
        try {
            List<Stock> list = iStockService.getList();
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
            String productName = map.get("productName").toString();

            List<Stock> list = iStockService.getList(productName);

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
    @Transactional
    public ResultInfo update(@RequestBody String stockJson){
        try {
            Stock stock = DecodeUtil.decodeToJson(stockJson,Stock.class);

            if (iStockService.update(stock)) {
                return ResultInfo.success("修改成功");
            } else {
                return ResultInfo.success("未修改", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改失败：{}", e.getMessage());
            log.error("参数：{}", stockJson);
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/delete")
    public ResultInfo delete(@RequestBody HashMap map){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"),Integer.class);
            if (iStockService.delete(idList)) {
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
