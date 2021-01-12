package com.zx.pro.controller;

import com.zx.pro.entity.MatterOrder;
import com.zx.pro.service.IMatterOrderService;
import com.zx.pro.util.ResultInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * a
 */
@RestController
@RequestMapping("/matterOrder")
public class MatterOrderController {

    @Resource
    private IMatterOrderService iMatterOrderService;

    /**
     * 查询物料订单表
     * @return 物料订单表信息的集合
     */
    @PostMapping("/getMatterOrderList")
    public ResultInfo getMatterOrderList(){
        List<MatterOrder> matterOrderList=null;
        try {
            matterOrderList=iMatterOrderService.getMatterOrder();
            return ResultInfo.success("获取成功",matterOrderList);
        }catch (Exception e){
            return ResultInfo.error("错误!");
        }
    }

    /**
     * 修改物料订单表
     * @param matterOrder 修改过的物料订单表对象
     * @return 是否修改成功
     */
    @PostMapping("/updateMatterOrder")
    public boolean updateMatterOrder(MatterOrder matterOrder){
        boolean updateMatterOrder=false;
        try {
            updateMatterOrder=iMatterOrderService.updateMatterOrder(matterOrder);
            return updateMatterOrder;
        }catch (Exception e){
            return updateMatterOrder;
        }
    }

    /**
     * 添加物料订单表
     * @param matterOrder 添加物料订单表对象
     * @return 是否添加成功
     */
    @PostMapping("/addMatterOrder")
    public boolean addMatterOrder(MatterOrder matterOrder){
        boolean addMatterOrder=false;
        try {
            addMatterOrder=iMatterOrderService.addMatterOrder(matterOrder);
            return addMatterOrder;
        }catch (Exception e){
            return addMatterOrder;
        }
    }

    /**
     * 删除
     * @param id 根据id删除
     * @return 是否删除成功
     */
    @PostMapping("/deleteMatterOrder")
    public boolean deleteMatterOrder(int id){
        boolean deleteMatterOrder=false;
        try {
            deleteMatterOrder=iMatterOrderService.deleteMatterOrder(id);
            return deleteMatterOrder;
        }catch (Exception e){
            return deleteMatterOrder;
        }
    }

    public ResultInfo getMatterInfoItemListByOrderId(){
        List<MatterOrder> matterInfoItemList=null;
        try {
            matterInfoItemList=iMatterOrderService
            return ResultInfo.success("获取成功",matterInfoItemList);
        }catch (Exception e){
            return ResultInfo.error("错误!");
        }
    }

}
