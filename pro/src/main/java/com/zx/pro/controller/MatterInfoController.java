package com.zx.pro.controller;

import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.MatterInfoItem;
import com.zx.pro.service.IMatterInfoService;
import com.zx.pro.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author dai
 */
@Slf4j
@RestController
@RequestMapping("/matter_info")
public class MatterInfoController {

    @Autowired
    private IMatterInfoService iMatterInfoService;

    /**
     * 查询物料信息
     *
     * @return 物料信息的集合
     * @return ResultInfo
     */
    @PostMapping("/getList")
    public ResultInfo getList(HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("物料配置")){
                return ResultInfo.error(401,"无权限");
            }

            List<MatterInfo> matterInfoList = iMatterInfoService.getList();
            return ResultInfo.success("获取成功", matterInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }

    @RequestMapping("/selectListByProjectId")
    public ResultInfo getList(@RequestBody HashMap map,HttpSession session){
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("物料配置")){
                return ResultInfo.error(401,"无权限");
            }

            Integer projectId = Integer.parseInt(map.get("projectId").toString());

            return ResultInfo.success("获取成功", iMatterInfoService.getList(projectId));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误!");
        }
    }

    @RequestMapping("/selectListOfUseByProjectId")
    public ResultInfo getListOfUse(@RequestBody HashMap map,HttpSession session){
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("物料配置")){
                return ResultInfo.error(401,"无权限");
            }

            Integer projectId = Integer.parseInt(map.get("projectId").toString());

            return ResultInfo.success("获取成功", iMatterInfoService.getListOfUse(projectId));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误!");
        }
    }

    @RequestMapping("/selectListByProjectName")
    public ResultInfo getListProjectName(String projectName,HttpSession session){
        List<MatterInfoItem>getList=null;
        try{
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("物料配置")){
                return ResultInfo.error(401,"无权限");
            }

            getList=iMatterInfoService.getListByProjectName(projectName);
            return ResultInfo.success("获取成功", getList);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            log.error("参数：{}", projectName);
            return ResultInfo.error("错误");
        }

    }

    /**
     * 修改
     *
     * @param matterInfoJson 修改过的物料对象
     * @return ResultInfo
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultInfo update(@RequestBody String matterInfoJson,HttpSession session) {
        MatterInfo matterInfo=null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isUpdate("物料配置")){
                return ResultInfo.error(401,"无权限");
            }

            matterInfo= DecodeUtil.decodeToJson(matterInfoJson, MatterInfo.class);
            if (iMatterInfoService.update(matterInfo)) {
                return ResultInfo.success("修改成功", matterInfo);
            } else {
                return ResultInfo.success("未修改", matterInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改失败：{}", e.getMessage());
            log.error("参数：{}", matterInfo);
            return ResultInfo.error("修改失败");
        }
    }

    /**
     * 添加物料
     *
     * @param map 添加物料的对象
     * @return ResultInfo
     */
    @PostMapping("/add")
    public ResultInfo add(@RequestBody HashMap map,HttpSession session) {
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isAdd("物料配置")){
                return ResultInfo.error(401,"无权限");
            }

            MatterInfo matterInfo=GsonUtil.toEntity(gsonUtil.get("addMatterInfo"),MatterInfo.class);
            matterInfo = iMatterInfoService.add(matterInfo);
            if (StringUtils.isNotNull(matterInfo)) {
                return ResultInfo.success("添加成功", matterInfo);
            } else {
                return ResultInfo.success("未添加", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("添加失败");
        }
    }

    /**
     * 删除物料
     *
     * @param map 根据id删除
     * @return ResultInfo
     */
    @PostMapping("/delete")
    public ResultInfo delete(@RequestBody HashMap map,HttpSession session) {

        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"), Integer.class);
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isDelete("物料配置")){
                return ResultInfo.error(401,"无权限");
            }

            if (iMatterInfoService.delete(idList)) {
                return ResultInfo.success("删除成功", idList);
            } else {
                return ResultInfo.success("未删除", idList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除失败：{}", e.getMessage());
            log.error("参数：{}", idList);
            return ResultInfo.error("删除失败");
        }
    }
}
