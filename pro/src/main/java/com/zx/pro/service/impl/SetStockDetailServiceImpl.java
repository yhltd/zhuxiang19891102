package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.SetStockDetail;
import com.zx.pro.entity.Stock;
import com.zx.pro.entity.UserInfo;
import com.zx.pro.mapper.SetStockDetailMapper;
import com.zx.pro.service.ISetStockDetailService;
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
public class SetStockDetailServiceImpl extends ServiceImpl<SetStockDetailMapper, SetStockDetail> implements ISetStockDetailService {

    @Autowired
    private IWorkOrderInfoService iWorkOrderInfoService;

    @Autowired
    private SetStockDetailMapper setStockDetailMapper;

    @Autowired
    private IStockService iStockService;

    @Override
    public List<SetStockDetail> getList() {
        return setStockDetailMapper.getList();
    }

    @Override
    public List<SetStockDetail> getList(String setOrder, String productName) {
        return setStockDetailMapper.selectList(setOrder, productName);
    }

    @Override
    public boolean add(String workOrder, UserInfo userInfo, List<SetStockDetail> setStockDetailList) {
        String setOrder = OrderUtil.getOrder("SS");
        LocalDateTime createTime = LocalDateTime.now();

        for (SetStockDetail setStockDetail : setStockDetailList) {
            //入库单号
            setStockDetail.setSetOrder(setOrder);
            //创建时间
            setStockDetail.setCreateTime(createTime);
            //入库人
            setStockDetail.setMan(userInfo.getName());


            //入库
            Stock stock = new Stock();
            //产品id
            stock.setMatterId(setStockDetail.getProductInfoId());
            //入库数量
            stock.setStockNum(setStockDetail.getSetNum());
            if(!iStockService.addOrUpdate(stock,true)){
                return false;
            }
        }

        //插入入库明细
        if(this.saveBatch(setStockDetailList, 50)){
            return iWorkOrderInfoService.updateState(workOrder,"1");
        }
        return false;
    }

    @Override
    public boolean update(SetStockDetail setStockDetail) {
        return this.updateById(setStockDetail);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        return this.removeByIds(idList);
    }
}
