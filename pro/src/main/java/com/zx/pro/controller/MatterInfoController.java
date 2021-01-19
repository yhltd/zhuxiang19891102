package com.zx.pro.controller;

import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.ProjectInfo;
import com.zx.pro.service.IMatterInfoService;
import com.zx.pro.util.ResultInfo;
import com.zx.pro.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public ResultInfo getMatterInfoList() {
        try {
            List<MatterInfo> matterInfoList = iMatterInfoService.getList();
            return ResultInfo.success("获取成功", matterInfoList);
        } catch (Exception e) {
            log.error("获取失败：{}", e.getMessage());
            return ResultInfo.error("错误!");
        }
    }

    @RequestMapping("/selectListByProjectId")
    public ResultInfo getMatterInfoList(@RequestBody HashMap map){
        try {
            Integer projectId = Integer.parseInt(map.get("projectId").toString());

            return ResultInfo.success("获取成功", iMatterInfoService.getList(projectId));
        } catch (Exception e) {
            log.error("获取失败：{}", e.getMessage());
            log.error("参数：{}", map);
            return ResultInfo.error("错误!");
        }
    }

    /**
     * 修改
     *
     * @param matterInfo 修改过的物料对象
     * @return ResultInfo
     */
    @PostMapping("/update")
    public ResultInfo updateMatterInfo(MatterInfo matterInfo) {
        try {
            if (iMatterInfoService.update(matterInfo)) {
                return ResultInfo.success("修改成功", matterInfo);
            } else {
                return ResultInfo.success("未修改", matterInfo);
            }
        } catch (Exception e) {
            log.error("修改失败：{}", e.getMessage());
            log.error("参数：{}", matterInfo);
            return ResultInfo.error("修改失败");
        }
    }

    /**
     * 添加物料
     *
     * @param matterInfo 添加物料的对象
     * @return ResultInfo
     */
    @PostMapping("/add")
    public ResultInfo addMatterInfo(MatterInfo matterInfo) {
        try {
            matterInfo = iMatterInfoService.add(matterInfo);

            if (StringUtils.isNotNull(matterInfo)) {
                return ResultInfo.success("添加成功", matterInfo);
            } else {
                return ResultInfo.success("未添加", null);
            }
        } catch (Exception e) {
            log.error("添加失败：{}", e.getMessage());
            log.error("参数：{}", matterInfo);
            return ResultInfo.error("添加失败");
        }
    }

    /**
     * 删除物料
     *
     * @param id 根据id删除
     * @return ResultInfo
     */
    @PostMapping("/delete")
    public ResultInfo deleteMatterInfo(int id) {
        try {
            if (iMatterInfoService.delete(id)) {
                return ResultInfo.success("删除成功", id);
            } else {
                return ResultInfo.success("未删除", id);
            }
        } catch (Exception e) {
            log.error("删除失败：{}", e.getMessage());
            log.error("参数：{}", id);
            return ResultInfo.error("删除失败");
        }
    }

}
