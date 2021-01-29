package com.zx.pro.controller;

import com.zx.pro.entity.MatterProjectChange;
import com.zx.pro.entity.MatterProjectChangeItem;
import com.zx.pro.service.IMatterProjectChangeService;
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
@RequestMapping("/matter_project_change")
public class MatterProjectChangeController {

    @Autowired
    private IMatterProjectChangeService iMatterProjectChangeService;

    @RequestMapping("/post_list")
    public ResultInfo postList(HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("项目物料需求变化")) {
                return ResultInfo.error(401, "无权限");
            }

            List<MatterProjectChangeItem> list = iMatterProjectChangeService.getList();

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

    @RequestMapping("/select_list")
    public ResultInfo postList(@RequestBody HashMap map, HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("项目物料需求变化表")) {
                return ResultInfo.error(401, "无权限");
            }

            //项目名称
            String projectName = map.get("projectName").toString();
            //物料编码
            String code = map.get("code").toString();
            //修改人
            String updateMan = map.get("updateMan").toString();

            List<MatterProjectChangeItem> list = iMatterProjectChangeService.getList(projectName, code, updateMan);

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
    public ResultInfo update(@RequestBody String matterProductChangeJson, HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isUpdate("项目物料需求变化表")) {
                return ResultInfo.error(401, "无权限");
            }

            MatterProjectChange matterProjectChange = DecodeUtil.decodeToJson(matterProductChangeJson,
                    MatterProjectChange.class, "updateTime");

            if (iMatterProjectChangeService.update(matterProjectChange)) {
                return ResultInfo.success("修改成功", matterProjectChange);
            } else {
                return ResultInfo.success("未修改");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改失败：{}", e.getMessage());
            log.error("参数：{}", matterProductChangeJson);
            return ResultInfo.error("修改失败");
        }
    }

    @RequestMapping("/delete")
    public ResultInfo delete(@RequestBody HashMap map, HttpSession session) {
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));

        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isDelete("项目物料需求变化表")) {
                return ResultInfo.error(401, "无权限");
            }

            List<Integer> idList = GsonUtil.toList(gsonUtil.get("idList"), Integer.class);

            if (iMatterProjectChangeService.delete(idList)) {
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
