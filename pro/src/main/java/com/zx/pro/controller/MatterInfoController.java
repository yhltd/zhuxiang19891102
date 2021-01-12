package com.zx.pro.controller;

import com.zx.pro.entity.MatterInfo;
import com.zx.pro.service.IMatterInfoService;
import com.zx.pro.util.ResultInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/matterInfo")
public class MatterInfoController {

    @Resource
    private IMatterInfoService iMatterInfoService;

    /**
     * 查询物料信息
     * @return 物料信息的集合
     */
    @GetMapping("/getMatterInfoList")
    public ResultInfo getMatterInfoList(){
        List<MatterInfo> matterInfoList=null;
        try {
            matterInfoList=iMatterInfoService.getMatterInfoList();
            return ResultInfo.success("获取成功",matterInfoList);
        }catch (Exception e){
            return ResultInfo.error("错误!");
        }
    }

    /**
     * 修改
     * @param matterInfo 修改过的物料对象
     * @return 是否修改成功
     */
    @PostMapping("/updateMatterInfo")
    public boolean updateMatterInfo(MatterInfo matterInfo){
        boolean updateMatterInfo=false;
        try {
            updateMatterInfo=iMatterInfoService.updateMatterInfo(matterInfo);
            return updateMatterInfo;
        }catch (Exception e){
            return updateMatterInfo;
        }
    }

    /**
     * 添加物料
     * @param matterInfo 添加物料的对象
     * @return 是否添加成功
     */
    @PostMapping("/addMatterInfo")
    public boolean addMatterInfo(MatterInfo matterInfo){
        boolean addMatterInfo=false;
        try {
            addMatterInfo=iMatterInfoService.addMatterInfo(matterInfo);
            return addMatterInfo;
        }catch (Exception e){
            return addMatterInfo;
        }
    }

    /**
     * 删除物料
     * @param id 根据id删除
     * @return 是否删除成功
     */
    @PostMapping("/deleteMatterInfo")
    public boolean deleteMatterInfo(int id){
        boolean deleteMatterInfo=false;
        try {
            deleteMatterInfo=iMatterInfoService.deleteMatterInfo(id);
            return deleteMatterInfo;
        }catch (Exception e){
            return deleteMatterInfo;
        }
    }

}
