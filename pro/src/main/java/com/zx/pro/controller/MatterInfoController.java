package com.zx.pro.controller;

import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.MatterInfoItem;
import com.zx.pro.entity.ProjectInfo;
import com.zx.pro.service.IMatterInfoService;
import com.zx.pro.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
     * @return ResultInfo
     */
    @PostMapping("/getList")
    public ResultInfo getList(HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("物料配置")) {
                return ResultInfo.error(401, "无权限");
            }

            List<MatterInfo> list = iMatterInfoService.getList();
            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("获取成功", list);
            } else {
                return ResultInfo.success("获取失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }

    @RequestMapping("/selectListByProjectId")
    public ResultInfo getList(HttpSession session,@RequestBody HashMap map) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("物料配置")) {
                return ResultInfo.error(401, "无权限");
            }

            Integer projectId = Integer.parseInt(map.get("projectId").toString());

            List<MatterInfoItem> list = iMatterInfoService.getList(projectId);
            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("获取成功", list);
            } else {
                return ResultInfo.success("获取失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误!");
        }
    }

    @RequestMapping("/selectListOfUseByProjectId")
    public ResultInfo getListOfUse(HttpSession session,@RequestBody HashMap map) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isAdd("订单汇总")) {
                return ResultInfo.error(401, "无权限");
            }

            Integer projectId = Integer.parseInt(map.get("projectId").toString());

            List<MatterInfoItem> list = iMatterInfoService.getListOfUse(projectId);
            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("获取成功", list);
            } else {
                return ResultInfo.success("获取失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误!");
        }
    }

    @RequestMapping("/selectListByProductId")
    public ResultInfo getListByProductId(HttpSession session,@RequestBody HashMap map) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("订单汇总")) {
                return ResultInfo.error(401, "无权限");
            }

            Integer productId = Integer.parseInt(map.get("productId").toString());

            List<MatterInfoItem> list = iMatterInfoService.getListByProductId(productId);
            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("获取成功", list);
            } else {
                return ResultInfo.success("获取失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误!");
        }
    }

    @PostMapping("/update")
    public ResultInfo update(HttpSession session,@RequestBody String matterInfoJson) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isUpdate("物料配置")) {
                return ResultInfo.error(401, "无权限");
            }

            MatterInfo matterInfo = DecodeUtil.decodeToJson(matterInfoJson, MatterInfo.class);

            if (iMatterInfoService.update(matterInfo)) {
                return ResultInfo.success("修改成功", matterInfo);
            } else {
                return ResultInfo.success("未修改", matterInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改失败：{}", e.getMessage());
            log.error("参数：{}", matterInfoJson);
            return ResultInfo.error("修改失败");
        }
    }

    @PostMapping("/add")
    public ResultInfo add(HttpSession session,@RequestBody String matterInfoJson) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isAdd("物料配置")) {
                return ResultInfo.error(401, "无权限");
            }

            MatterInfo matterInfo = DecodeUtil.decodeToJson(matterInfoJson, MatterInfo.class);

            if (iMatterInfoService.add(matterInfo)) {
                return ResultInfo.success("添加成功", matterInfo);
            } else {
                return ResultInfo.success("未添加", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加失败：{}", e.getMessage());
            log.error("参数：{}", matterInfoJson);
            return ResultInfo.error("添加失败");
        }
    }

    @PostMapping("/delete")
    public ResultInfo delete(HttpSession session,@RequestBody HashMap map) {
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isDelete("物料配置")) {
                return ResultInfo.error(401, "无权限");
            }

            List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"), Integer.class);

            if (iMatterInfoService.delete(idList)) {
                return ResultInfo.success("删除成功");
            } else {
                return ResultInfo.success("未删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("删除失败");
        }
    }

}
