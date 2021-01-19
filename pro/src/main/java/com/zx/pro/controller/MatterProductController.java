package com.zx.pro.controller;

import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.MatterProduct;
import com.zx.pro.entity.UserInfo;
import com.zx.pro.mapper.MatterProductMapper;
import com.zx.pro.service.IMatterProductService;
import com.zx.pro.util.GsonUtil;
import com.zx.pro.util.ResultInfo;
import com.zx.pro.util.SessionUtil;
import com.zx.pro.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.HsqlMaxValueIncrementer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
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
        try {
            return ResultInfo.success("获取成功", iMatterProductService.getList());
        }catch (Exception e){
            log.error("获取失败：{}",e.getMessage());
            return ResultInfo.error("获取失败");
        }
    }

    @RequestMapping("/selectList")
    public ResultInfo getMatterInfoList(@RequestBody HashMap map) {
        try {
            Integer projectId = Integer.parseInt(map.get("projectId").toString());

            return ResultInfo.success("获取成功", iMatterProductService.getList(projectId));
        } catch (Exception e) {
            log.error("获取失败：{}", e.getMessage());
            return ResultInfo.error("错误!");
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
            log.error("修改失败：{}",e.getMessage());
            log.error("参数：{}",matterProduct);
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
            matterProduct = iMatterProductService.add(matterProduct);
            if(StringUtils.isNotNull(matterProduct)){
                return ResultInfo.success("新增成功",matterProduct);
            }else{
                return ResultInfo.success("未新增",null);
            }
        }catch (Exception e){
            log.error("添加失败：{}",e.getMessage());
            log.error("参数：{}",matterProduct);
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
                return ResultInfo.success("删除成功",id);
            }else{
                return ResultInfo.success("未删除",id);
            }
        }catch (Exception e){
            log.error("删除失败：{}",e.getMessage());
            log.error("参数：{}",id);
            return ResultInfo.error("删除失败");
        }
    }

    @RequestMapping("/change")
    @Transactional
    public ResultInfo change(HttpSession session, @RequestBody HashMap map){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try {
            //修改后的list
            List<MatterProduct> newList = GsonUtil.toList(gsonUtil.get("newList"), MatterProduct.class);
            //项目id
            Integer projectId = Integer.parseInt(gsonUtil.get("projectId"));
            //当前登陆用户信息
            UserInfo userInfo = GsonUtil.toEntity(SessionUtil.getToken(session),UserInfo.class);

            if(iMatterProductService.change(newList,projectId,userInfo)){
                return ResultInfo.success("修改成功",null);
            }else{
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultInfo.success("未修改",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("删除失败：{}",e.getMessage());
            log.error("参数：{}",map);
            return ResultInfo.error("修改失败");
        }
    }
}
