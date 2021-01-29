package com.zx.pro.controller;

import com.zx.pro.entity.UserInfo;
import com.zx.pro.service.IUserInfoService;
import com.zx.pro.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dai
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserInfoService iUserInfoService;


    @RequestMapping("/login")
    public ResultInfo login(HttpSession session, String name, String pwd) {
        try {
            //获取user
            Map<String, Object> map = iUserInfoService.login(name, pwd);

            //为Null则查询不到
            if (StringUtils.isEmpty(map)) {
                SessionUtil.remove(session, "token");
                SessionUtil.remove(session, "power");
                return ResultInfo.error(-1, "用户名密码错误");
            } else {
                SessionUtil.setToken(session, map.get("token").toString());
                SessionUtil.setPower(session, StringUtils.cast(map.get("power")));
                return ResultInfo.success("登陆成功", null);
            }
        } catch (Exception e) {
            log.error("登陆失败：{}", e.getMessage());
            log.error("参数：{}", name);
            log.error("参数：{}", pwd);
            return ResultInfo.error("错误!");
        }
    }

    /**
     * 查询
     *
     * @return ResultInfo
     */
    @RequestMapping("/getList")
    public ResultInfo getList(HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("员工管理")) {
                return ResultInfo.error(401, "无权限");
            }

            List<UserInfo> getList = iUserInfoService.getList();
            return ResultInfo.success("获取成功", getList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }

    /**
     * 添加
     *
     * @param map
     * @return ResultInfo
     */
    @RequestMapping("/add")
    public ResultInfo add(@RequestBody HashMap map, HttpSession session) {
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isAdd("员工管理")) {
                return ResultInfo.error(401, "无权限");
            }

            UserInfo userInfo = GsonUtil.toEntity(gsonUtil.get("addUserInfo"), UserInfo.class);
            userInfo = iUserInfoService.add(userInfo);
            if (StringUtils.isNotNull(userInfo)) {
                return ResultInfo.success("添加成功", userInfo);
            } else {
                return ResultInfo.success("添加失败", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("添加失败");
        }
    }

    /**
     * 修改
     *
     * @param userInfoJson
     * @return ResultInfo
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultInfo update(@RequestBody String userInfoJson, HttpSession session) {
        UserInfo userInfo = null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isUpdate("员工管理")) {
                return ResultInfo.error(401, "无权限");
            }

            userInfo = DecodeUtil.decodeToJson(userInfoJson, UserInfo.class);
            if (iUserInfoService.update(userInfo)) {
                return ResultInfo.success("修改成功", userInfo);
            } else {
                return ResultInfo.success("修改失败", userInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改失败：{}", e.getMessage());
            log.error("参数：{}", userInfo);
            return ResultInfo.error("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param map
     * @return ResultInfo
     */
    @RequestMapping("/delete")
    public ResultInfo delete(@RequestBody HashMap map, HttpSession session) {
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"), Integer.class);
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isDelete("员工管理")) {
                return ResultInfo.error(401, "无权限");
            }

            if (iUserInfoService.delete(idList)) {
                return ResultInfo.success("删除成功", idList);
            } else {
                return ResultInfo.success("删除失败", idList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除失败：{}", e.getMessage());
            log.error("参数：{}", idList);
            return ResultInfo.error("删除失败");
        }
    }

    @RequestMapping("/updatePwd")
    public ResultInfo updatePwd(HttpSession session, @RequestBody HashMap map) {
        try {
            String oldPwd = map.get("oldPwd").toString();
            String newPwd = map.get("newPwd").toString();

            UserInfo userInfo = GsonUtil.toEntity(SessionUtil.getToken(session), UserInfo.class);
            if(!oldPwd.equals(userInfo.getPwd())){
                return ResultInfo.success("旧密码错误");
            }
            userInfo.setPwd(newPwd);
            if (iUserInfoService.update(userInfo)) {
                SessionUtil.setToken(session,GsonUtil.toJson(userInfo));
                return ResultInfo.success("修改成功");
            } else {
                return ResultInfo.success("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("修改失败");
        }
    }

}
