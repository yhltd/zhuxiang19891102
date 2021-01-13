package com.zx.pro.controller;

import com.zx.pro.entity.MatterProduct;
import com.zx.pro.mapper.MatterProductMapper;
import com.zx.pro.service.IMatterProductService;
import com.zx.pro.util.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 物料和项目、产品的接口
 *
 * @author dai
 */
@Slf4j
@RestController
@RequestMapping("/matter_product")
public class MatterProductController {

    @Autowired
    private IMatterProductService iMatterProductService;

    /**
     * 查询物料订单表
     * @return ResultInfo
     */
    @PostMapping("/getList")
    public ResultInfo getMatterProductList(){
        List<MatterProduct> matterProductList;
        try {
            matterProductList = iMatterProductService.getList();
            return ResultInfo.success("获取成功", matterProductList);
        }catch (Exception e){
            log.error("获取物料集合失败：{}，参数：[]",e.getMessage());
            return ResultInfo.error("获取失败");
        }
    }

    /**
     * 修改物料订单表
     * @param matterProduct 修改过的物料订单表对象
     * @return ResultInfo
     */
    @PostMapping("/update")
    public ResultInfo updateMatterProduct(MatterProduct matterProduct){
        try {
            if(iMatterProductService.update(matterProduct)){
                return ResultInfo.success("修改成功",matterProduct);
            }else{
                return ResultInfo.success("未修改",matterProduct);
            }
        }catch (Exception e){
            log.error("修改失败：{}，参数：[matterProduct:{}]",e.getMessage(),matterProduct);
            return ResultInfo.error("修改失败");
        }
    }

    /**
     * 添加物料订单表
     * @param matterProduct 添加物料订单表对象
     * @return ResultInfo
     */
    @PostMapping("/add")
    public ResultInfo addMatterProduct(MatterProduct matterProduct){
        try {
            if(iMatterProductService.add(matterProduct)){
                return ResultInfo.success("新增成功",matterProduct);
            }else{
                return ResultInfo.success("未新增",matterProduct);
            }
        }catch (Exception e){
            log.error("添加失败：{}，参数：[matterProduct:{}]",e.getMessage(),matterProduct);
            return ResultInfo.error("新增失败");
        }
    }

    /**
     * 删除
     * @param id 根据id删除
     * @return ResultInfo
     */
    @PostMapping("/delete")
    public ResultInfo deleteMatterProduct(int id){
        try {
            if(iMatterProductService.delete(id)){
                return ResultInfo.success("删除成功",null);
            }else{
                return ResultInfo.success("未删除",null);
            }
        }catch (Exception e){
            log.error("删除失败：{}，参数：[id:{}]",e.getMessage(),id);
            return ResultInfo.error("删除失败");
        }
    }
}
