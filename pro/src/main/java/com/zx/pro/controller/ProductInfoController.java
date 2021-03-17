package com.zx.pro.controller;

import com.zx.pro.entity.*;
import com.zx.pro.service.IProductInfoService;
import com.zx.pro.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/product_info")
public class ProductInfoController {

    @Autowired
    private IProductInfoService iProductInfoService;

    @RequestMapping("/post_list")
    public ResultInfo postList(HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("订单明细")){
                return ResultInfo.error(401,"无权限");
            }

            return ResultInfo.success("获取成功", iProductInfoService.getList());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/select_list")
    public ResultInfo selectList(HttpSession session,@RequestBody HashMap map) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("订单明细")){
                return ResultInfo.error(401,"无权限");
            }

            String orderId = map.get("orderId").toString();
            String productName = map.get("productName").toString();

            List<ProductInfo> list = iProductInfoService.getList(orderId, productName);
            for (ProductInfo p:list
                 ) {
                System.out.println(p.toString());
            }
            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("查询成功", list);
            } else {
                return ResultInfo.success("查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/update")
    public ResultInfo update(HttpSession session, @RequestBody String OrsermatterJson){
        return ResultInfo.error();
    }

//    @RequestMapping("/update")
//    public ResultInfo update(HttpSession session, @RequestBody String productInfoJson){
//        try{
//            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
//            if(!powerUtil.isUpdate("订单明细")){
//                return ResultInfo.error(401,"无权限");
//            }
//
//            ProductInfo productInfo = DecodeUtil.decodeToJson(productInfoJson,
//                    ProductInfo.class);
//
//            if(iProductInfoService.update(productInfo)){
//                return ResultInfo.success("修改成功",productInfo);
//            }else{
//                return ResultInfo.success("未修改");
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error("修改失败：{}",e.getMessage());
//            log.error("参数：{}",productInfoJson);
//            return ResultInfo.error("修改失败");
//        }
//    }

//    @RequestMapping("/delete")
//    public ResultInfo delete(HttpSession session,@RequestBody HashMap map){
//        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
//        try{
//            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
//            if(!powerUtil.isDelete("订单明细")){
//                return ResultInfo.error(401,"无权限");
//            }
//
//            List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"),Integer.class);
//
//            if(iProductInfoService.delete(idList)){
//                return ResultInfo.success("删除成功");
//            }else{
//                return ResultInfo.success("未删除");
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error("删除失败：{}",e.getMessage());
//            log.error("参数：{}",map);
//            return ResultInfo.error("删除失败");
//        }
//    }
}
