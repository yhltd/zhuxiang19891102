package com.zx.pro.controller;

import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.UserInfo;
import com.zx.pro.service.IMatterOrderService;
import com.zx.pro.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/matter_order")
public class MatterOrderController {

    @Autowired
    private IMatterOrderService matterOrderService;

    @RequestMapping("/post_list")
    public ResultInfo postList(HttpSession session){
        try{
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("订单明细")) {
                return ResultInfo.error(401, "无权限");
            }

            return ResultInfo.success("获取成功", matterOrderService.postList());
        }catch(Exception e){
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }

    @RequestMapping("/select_list")
    public ResultInfo selectList(HttpSession session,@RequestBody HashMap map){
        try{
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("订单明细")) {
                return ResultInfo.error(401, "无权限");
            }

            String orderId = map.get("orderId").toString();
            String code = map.get("code").toString();

            return ResultInfo.success("获取成功", matterOrderService.selectList(orderId, code));
        }catch(Exception e){
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }

    @RequestMapping("/update")
    public ResultInfo update(HttpSession session,@RequestBody HashMap map){
        try{
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("订单明细")) {
                return ResultInfo.error(401, "无权限");
            }

            int uid = Integer.parseInt(map.get("uid").toString());
            int id = Integer.parseInt(map.get("id").toString());
            String orderId = map.get("orderId").toString();
            int oldNum = Integer.parseInt(map.get("oldNum").toString());
            int newNum = Integer.parseInt(map.get("newNum").toString());

            UserInfo userInfo = GsonUtil.toEntity(SessionUtil.getToken(session),UserInfo.class);

            if(matterOrderService.update(userInfo, id, orderId, uid, oldNum, newNum)){
                return ResultInfo.success("修改成功");
            }else{
                return ResultInfo.success("修改失败");
            }

        }catch(Exception e){
            e.printStackTrace();
            log.error("修改失败：{}", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }

    @RequestMapping("/delete")
    public ResultInfo delete(HttpSession session,@RequestBody HashMap map){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try{
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("订单明细")) {
                return ResultInfo.error(401, "无权限");
            }

            List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"),Integer.class);

            if(matterOrderService.delete(idList)){
                return ResultInfo.success("删除成功");
            }else{
                return ResultInfo.success("删除失败");
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("删除失败：{}", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }
}
