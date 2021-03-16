package com.zx.pro.controller;

import com.zx.pro.entity.*;
import com.zx.pro.service.IProductInfoService;
import com.zx.pro.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

//    @RequestMapping("/post_list")
//    public ResultInfo postList(HttpSession session) {
//        try {
//            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
//            if(!powerUtil.isSelect("订单明细")){
//                return ResultInfo.error(401,"无权限");
//            }
//
//            List<ProductInfo> list = iProductInfoService.getList();
//            if (StringUtils.isNotNull(list)) {
//                return ResultInfo.success("获取成功", list);
//            } else {
//                return ResultInfo.success("获取失败");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("获取失败：{}", e.getMessage());
//            return ResultInfo.error("错误");
//        }
//    }

    /*
    * 新增加
    * */
        @RequestMapping("/post_list")
        public ResultInfo postList(HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("订单明细")){
                return ResultInfo.error(401,"无权限");
            }

            List<Order_matter> list = iProductInfoService.getmoList();
            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("获取成功", list);
            } else {
                return ResultInfo.success("获取失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            return ResultInfo.error("错误");
        }
    }
    /*
     * 新增加
     * */




//    @RequestMapping("/select_list")
//    public ResultInfo selectList(HttpSession session,@RequestBody HashMap map) {
//        try {
//            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
//            if(!powerUtil.isSelect("订单明细")){
//                return ResultInfo.error(401,"无权限");
//            }
//
//            String orderId = map.get("orderId").toString();
//            String productName = map.get("productName").toString();
//
//            List<ProductInfo> list = iProductInfoService.getList(orderId, productName);
//            for (ProductInfo p:list
//                 ) {
//                System.out.println(p.toString());
//            }
//            if (StringUtils.isNotNull(list)) {
//                return ResultInfo.success("查询成功", list);
//            } else {
//                return ResultInfo.success("查询失败");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("获取失败：{}", e.getMessage());
//            log.error("参数：{}", map);
//            return ResultInfo.error("错误");
//        }
//    }



    /*
    * 新修改
    * */
    @RequestMapping("/select_list")
    public ResultInfo selectList(HttpSession session,@RequestBody HashMap map) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("订单明细")){
                return ResultInfo.error(401,"无权限");
            }

            String orderId = map.get("orderId").toString();
            String productName = map.get("productName").toString();

            List<Order_matter> list = iProductInfoService.getmoonList(orderId, productName);
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
        System.out.println(OrsermatterJson);
        try{
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isUpdate("订单明细")){
                return ResultInfo.error(401,"无权限");
            }

            Order_matter orderMatter = DecodeUtil.decodeToJson(OrsermatterJson,
                    Order_matter.class);
            int id=iProductInfoService.getid(orderMatter.getMattername());
//            MatterOrder matterOrder=new MatterOrder();
//            matterOrder.setMatter_id(id);
//            matterOrder.setId(orderMatter.getId());
//            matterOrder.setNum(Integer.parseInt(orderMatter.getMatternum()));
//            matterOrder.setOrder_id(orderMatter.getOrderid());
            if (id>0){
                if (iProductInfoService.updatematterorder(id, orderMatter.getId(),Integer.parseInt(orderMatter.getMatternum()))!=0) {
                    return ResultInfo.success("修改成功", orderMatter);
                } else {
                    return ResultInfo.success("未修改");
                }
            }
            else {
                return ResultInfo.success("对不起，该物料不存在");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("修改失败：{}",e.getMessage());
            log.error("参数：{}",OrsermatterJson);
            return ResultInfo.error("修改失败");
        }
    }


    @RequestMapping("/delete")
    public ResultInfo delete(HttpSession session,@RequestBody HashMap map){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try{
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isDelete("订单明细")){
                return ResultInfo.error(401,"无权限");
            }
            int index=0;
            int index2=0;
            List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"),Integer.class);
            for (Integer id: idList) {
                index2=iProductInfoService.deleteorderid(id);
                index=iProductInfoService.deletemo(id);
            }
            if(index!=0&&index2!=0){
                return ResultInfo.success("删除成功");
            }else{
                return ResultInfo.success("未删除");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("删除失败：{}",e.getMessage());
            log.error("参数：{}",map);
            return ResultInfo.error("删除失败");
        }
    }

    /*
     * 新修改
     * */





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
