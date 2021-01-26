package com.zx.pro.controller;

import com.zx.pro.entity.UserInfo;
import com.zx.pro.entity.UserPower;
import com.zx.pro.service.IUserPowerService;
import com.zx.pro.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author wanghui
 * @date 2021/1/24 12:22
 */
@Slf4j
@RestController
@RequestMapping("/user_power")
public class UserPowerController {
    @Autowired
    private IUserPowerService iUserPowerService;

    @PostMapping("/getList")
    public ResultInfo getList(int id, HttpSession session){
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("员工管理")){
                return ResultInfo.error(401,"无权限");
            }

            List<UserPower> getList=iUserPowerService.getList(id);
            return ResultInfo.success("获取成功", getList);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }

    @PostMapping("/add")
    public ResultInfo add(@RequestBody HashMap map,HttpSession session){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isAdd("员工管理")){
                return ResultInfo.error(401,"无权限");
            }

            UserPower userPower=GsonUtil.toEntity(gsonUtil.get("addUserPower"), UserPower.class);
            userPower=iUserPowerService.add(userPower);
            if (StringUtils.isNotNull(userPower)) {
                return ResultInfo.success("添加成功", userPower);
            } else {
                return ResultInfo.success("添加失败", null);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("添加失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("添加失败");
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultInfo update(@RequestBody String userPowerJson,HttpSession session){
        UserPower userPower=null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isUpdate("员工管理")){
                return ResultInfo.error(401,"无权限");
            }

            userPower= DecodeUtil.decodeToJson(userPowerJson,UserPower.class);
            if (iUserPowerService.update(userPower)) {
                return ResultInfo.success("修改成功", userPower);
            } else {
                return ResultInfo.success("修改失败", userPower);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("修改失败：{}", e.getMessage());
            log.error("参数：{}", userPower);
            return ResultInfo.error("修改失败");
        }
    }

    @PostMapping("/delete")
    public ResultInfo delete(@RequestBody HashMap map,HttpSession session){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"), Integer.class);
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isDelete("员工管理")){
                return ResultInfo.error(401,"无权限");
            }

            if (iUserPowerService.delete(idList)) {
                return ResultInfo.success("删除成功", idList);
            } else {
                return ResultInfo.success("删除失败", idList);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除失败：{}", e.getMessage());
            log.error("参数：{}", idList);
            return ResultInfo.error("删除失败");
        }
    }




}
