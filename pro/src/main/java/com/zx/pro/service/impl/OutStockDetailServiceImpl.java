package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.OutStockDetail;
import com.zx.pro.entity.SetStockDetail;
import com.zx.pro.entity.Stock;
import com.zx.pro.entity.UserInfo;
import com.zx.pro.mapper.OutStockDetailMapper;
import com.zx.pro.mapper.ProductInfoMapper;
import com.zx.pro.mapper.SetStockDetailMapper;
import com.zx.pro.service.IOutStockDetailService;
import com.zx.pro.service.IStockService;
import com.zx.pro.service.IWorkOrderInfoService;
import com.zx.pro.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dai
 */
@Service
public class OutStockDetailServiceImpl extends ServiceImpl<OutStockDetailMapper, OutStockDetail> implements IOutStockDetailService {

    @Autowired
    private OutStockDetailMapper outStockDetailMapper;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private IStockService iStockService;

    @Override
    public List<OutStockDetail> getList() {
        return outStockDetailMapper.getList();
    }

    @Override
    public List<OutStockDetail> getList(String outOrder, String productName) {
        return outStockDetailMapper.selectList(outOrder, productName);
    }

    @Override
    public List<OutStockDetail> getList(String orderId) {
        return productInfoMapper.getList(orderId);
    }

    @Override
    public boolean add(UserInfo userInfo, List<OutStockDetail> outStockDetails) {
        String outOrder = OrderUtil.getOrder("OS");
        LocalDateTime createTime = LocalDateTime.now();

        for (OutStockDetail outStockDetail : outStockDetails) {
            //出库单号
            outStockDetail.setOutOrder(outOrder);
            //创建时间
            outStockDetail.setCreateTime(createTime);
            //出库人
            outStockDetail.setMan(userInfo.getName());


            //出库
            Stock stock = new Stock();
            //产品id
            stock.setMatter_id(outStockDetail.getMatter_id());
            //出库数量
            stock.setStockNum(outStockDetail.getOutNum());
            if(!iStockService.addOrUpdate(stock,false)){
                return false;
            }
        }

        //插入出库明细
        return this.saveBatch(outStockDetails, 50);
    }

    @Override
    public boolean update(OutStockDetail outStockDetail) {
        return this.updateById(outStockDetail);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        return this.removeByIds(idList);
    }
}
