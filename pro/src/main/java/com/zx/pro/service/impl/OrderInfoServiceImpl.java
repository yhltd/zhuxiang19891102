package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.*;
import com.zx.pro.mapper.OrderInfoMapper;
import com.zx.pro.service.IOrderInfoService;
import com.zx.pro.service.IProductInfoService;
import com.zx.pro.service.IProductMatterService;
import com.zx.pro.util.GsonUtil;
import com.zx.pro.util.OrderUtil;
import com.zx.pro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author dai
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private IProductInfoService iProductInfoService;

    @Autowired
    private IProductMatterService iProductMatterService;

    @Override
    public List<OrderInfoItem> getList() {
        return orderInfoMapper.getList();
    }

    @Override
    public List<OrderInfoItem> getList(String projectName, String orderId, String startDateStr, String endDateStr) {
        LocalDateTime startDate = StringUtils.isNotEmpty(startDateStr) ?
                LocalDateTime.parse(startDateStr) :
                StringUtils.MIN_DATETIME;

        LocalDateTime endDate = StringUtils.isNotEmpty(endDateStr) ?
                LocalDateTime.parse(endDateStr) :
                StringUtils.MAX_DATETIME;
        return orderInfoMapper.getList(projectName,orderId,startDate,endDate);
    }

    @Override
    public boolean update(OrderInfoItem orderInfoItem) {
        return this.updateById(StringUtils.cast(orderInfoItem));
    }

    @Override
    public boolean delete(List<Integer> idList) {
        return this.removeByIds(idList);
    }

    @Override
    public boolean add(OrderInfo orderInfo, List<HashMap> hashMapList){
        //订单号
        orderInfo.setOrderId(OrderUtil.getOrder("O"));
        //创建时间
        orderInfo.setCreateTime(LocalDateTime.now());
        //添加订单
        this.save(orderInfo);

        for(HashMap hashMap : hashMapList){
            //产品信息
            ProductInfo productInfo = new ProductInfo();
            //订单表id
            productInfo.setOrderInfoId(orderInfo.getId());
            //产品名称
            productInfo.setProductName(StringUtils.cast(hashMap.get("productName")));
            //产品数量
            productInfo.setProductNum(Double.parseDouble(hashMap.get("productNum").toString()));
            //添加产品
            iProductInfoService.add(productInfo);

            //获取所需物料信息
            String matterInfoJson = GsonUtil.toJson(hashMap.get("matterInfo"));
            //转List
            List<MatterInfoItem> matterInfoItemList = GsonUtil.toList(matterInfoJson, MatterInfoItem.class);
            //产品所需物料表集合
            List<ProductMatter> productMatterList = new ArrayList<>();

            for(MatterInfoItem matterInfoItem : matterInfoItemList){
                //产品所需物料
                ProductMatter productMatter = new ProductMatter();
                //产品信息表id
                productMatter.setProductInfoId(productInfo.getOrderInfoId());
                //项目物料表id
                productMatter.setMatterProjectId(matterInfoItem.getMatterProjectId());
                //数量
                productMatter.setNum(matterInfoItem.getNum());
                //单价
                productMatter.setPrice(matterInfoItem.getPrice());
                //添加到集合中
                productMatterList.add(productMatter);
            }
            //如果添加不成功直接return结束方法
            if(!iProductMatterService.add(productMatterList)){
                return false;
            }
        }
        return true;
    }
}
