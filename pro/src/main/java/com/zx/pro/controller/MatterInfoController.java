package com.zx.pro.controller;

import com.zx.pro.entity.MatterInfo;
import com.zx.pro.service.IMatterInfoService;
import com.zx.pro.util.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/matter_info")
public class MatterInfoController {

    @Autowired
    private IMatterInfoService iMatterInfoService;

    /**
     * 查询物料信息
     * @return 物料信息的集合
     */
    @GetMapping("/getList")
    public ResultInfo getMatterInfoList(){
        List<MatterInfo> matterInfoList=null;
        try {
            matterInfoList=iMatterInfoService.getList();
            return ResultInfo.success("获取成功",matterInfoList);
        }catch (Exception e){
            log.error("获取物料集合失败：{}，参数：[]",e.getMessage());
            return ResultInfo.error("错误!");
        }
    }

    /**
     * 修改
     * @param matterInfo 修改过的物料对象
     * @return 是否修改成功
     */
    @PostMapping("/update")
    public ResultInfo updateMatterInfo(MatterInfo matterInfo){
        try {
            if(iMatterInfoService.update(matterInfo)){
                return ResultInfo.success("修改成功",matterInfo);
            }else{
                return ResultInfo.success("未修改",matterInfo);
            }
        }catch (Exception e){
            log.error("修改物料失败：{}，参数：[matterInfo：{}]",e.getMessage(),matterInfo);
            return ResultInfo.error("修改失败");
        }
    }

    /**
     * 添加物料
     * @param matterInfo 添加物料的对象
     * @return 是否添加成功
     */
    @PostMapping("/add")
    public ResultInfo addMatterInfo(MatterInfo matterInfo){
        try {
            if(iMatterInfoService.add(matterInfo)){
                return ResultInfo.success("添加成功",matterInfo);
            }else{
                return ResultInfo.success("未添加",matterInfo);
            }
        }catch (Exception e){
            log.error("添加物料失败：{}，参数：[matterInfo:{}]",e.getMessage(),matterInfo);
            return ResultInfo.error("添加失败");
        }
    }

    /**
     * 删除物料
     * @param id 根据id删除
     * @return 是否删除成功
     */
    @PostMapping("/delete")
    public ResultInfo deleteMatterInfo(int id){
        try {
            if(iMatterInfoService.delete(id)){
                return ResultInfo.success("删除成功",null);
            }else{
                return ResultInfo.success("未删除",null);
            }
        }catch (Exception e){
            log.error("删除物料失败：{}，参数：[id:{}]",e.getMessage(),id);
            return ResultInfo.error("删除失败");
        }
    }

}
