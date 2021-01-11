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

    @PostMapping("/updateMatterInfo")
    public boolean updateMatterInfo(MatterInfo matterInfo){
        boolean updateMatter=false;
        try {
            updateMatter=iMatterInfoService.updateMatterInfo(matterInfo);
            return updateMatter;
        }catch (Exception e){
            return updateMatter;
        }
    }

    @PostMapping("/insertMatterInfo")
    public boolean insertMatterInfo(MatterInfo matterInfo){
        boolean insertMatterInfo=false;
        try {
            insertMatterInfo=iMatterInfoService.insertMatterInfo(matterInfo);
            return insertMatterInfo;
        }catch (Exception e){
            return insertMatterInfo;
        }
    }

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
