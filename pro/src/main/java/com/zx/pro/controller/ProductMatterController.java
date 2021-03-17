package com.zx.pro.controller;

import com.zx.pro.entity.ProductMatter;
import com.zx.pro.service.IProductMatterService;
import com.zx.pro.util.GsonUtil;
import com.zx.pro.util.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author dai
 */
@Slf4j
@RestController
@RequestMapping("/product_matter")
public class ProductMatterController {

    @Autowired
    private IProductMatterService iProductMatterService;

    @RequestMapping("/update")
    @Transactional
    public ResultInfo update(@RequestBody HashMap map){
        GsonUtil gsonUtil = new GsonUtil(GsonUtil.toJson(map));
        try{
            //产品id
            Integer productInfoId = Integer.parseInt(gsonUtil.get("productInfoId"));
            //要修改的产品物料集合
            List<ProductMatter> productMatterList = GsonUtil.toList(gsonUtil.get("productMatterList"),ProductMatter.class);

            if(iProductMatterService.update(productInfoId,productMatterList)){
                return ResultInfo.success("修改成功");
            }else{
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultInfo.success("未修改");
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("修改失败：{}",e.getMessage());
            log.error("参数：{}",map);
            return ResultInfo.error("修改失败");
        }
    }
}
