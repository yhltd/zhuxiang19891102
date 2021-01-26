package com.zx.pro.controller;

import com.zx.pro.entity.MatterProject;
import com.zx.pro.entity.ProjectInfo;
import com.zx.pro.service.IProjectInfoService;
import com.zx.pro.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author dai
 */
@Slf4j
@RestController
@RequestMapping("/project_info")
public class ProjectInfoController {

    @Autowired
    IProjectInfoService iProjectInfoService;

    @PostMapping("/getList")
    public ResultInfo getList(HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("项目信息")){
                return ResultInfo.error(401,"无权限");
            }

            List<ProjectInfo> list = iProjectInfoService.getList();
            return ResultInfo.success("获取成功", list);
        } catch (Exception e) {
            log.error("获取失败：{}", e.getMessage());
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping("/selectList")
    public ResultInfo getList(@RequestBody HashMap map,HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isSelect("项目信息")){
                return ResultInfo.error(401,"无权限");
            }

            String projectName = map.get("projectName").toString();
            String startDate = map.get("startDate").toString();
            String endDate = map.get("endDate").toString();

            List<ProjectInfo> list = iProjectInfoService.getList(projectName,startDate,endDate);

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


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultInfo update(@RequestBody String projectInfoJson,HttpSession session) {
        ProjectInfo projectInfo = null;
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isUpdate("项目信息")){
                return ResultInfo.error(401,"无权限");
            }

            projectInfo = DecodeUtil.decodeToJson(projectInfoJson, ProjectInfo.class, "createTime");
            if (iProjectInfoService.update(projectInfo)) {
                return ResultInfo.success("修改成功", projectInfo);
            } else {
                return ResultInfo.success("未修改", projectInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改失败：{}", e.getMessage());
            log.error("参数：{}", projectInfo);
            return ResultInfo.error("错误");
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResultInfo add(@RequestBody HashMap map,HttpSession session) {
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isAdd("项目信息")){
                return ResultInfo.error(401,"无权限");
            }

            ProjectInfo projectInfo = GsonUtil.toEntity(gsonUtil.get("projectInfo"),ProjectInfo.class);
            List<MatterProject> list = GsonUtil.toList(gsonUtil.get("matterProjectList"), MatterProject.class);

            projectInfo = iProjectInfoService.add(projectInfo, list);
            if (StringUtils.isNotNull(projectInfo)) {
                return ResultInfo.success("新增成功", projectInfo);
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

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Transactional
    public ResultInfo delete(@RequestBody HashMap map,HttpSession session){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if(!powerUtil.isDelete("项目信息")){
                return ResultInfo.error(401,"无权限");
            }

            List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"),Integer.class);
            if (iProjectInfoService.delete(idList)) {
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
