package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.Stock;
import com.zx.pro.entity.StockItem;
import com.zx.pro.mapper.StockMapper;
import com.zx.pro.service.IStockService;
import com.zx.pro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public boolean addOrUpdate(Stock stock, Boolean isSet) {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("stock_id").eq("matter_id",stock.getMatterId());
        Stock oldStock = this.getOne(queryWrapper);

        //如果已有商品，并且是入库操作
        if(StringUtils.isNotNull(oldStock)){
            //拼接Sql语句
            StringBuffer sqlBuffer = new StringBuffer("stock_num = stock_num");
            sqlBuffer.append(isSet ? "+" : "-");
            sqlBuffer.append(stock.getStockNum());

            //修改操作
            UpdateWrapper<Stock> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("matter_id",stock.getMatterId());
            updateWrapper.setSql(sqlBuffer.toString());
            return this.update(updateWrapper);
        }else{
            if(isSet){
                return this.save(stock);
            }
        }
        return false;
    }

    @Override
    public List<StockItem> getList() {
        return stockMapper.getList();
    }

    @Override
    public List<StockItem> getList(String code) {
        return stockMapper.selectList(code);
    }

    @Override
    public boolean update(Stock stock) {
        UpdateWrapper<Stock> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("stock_id",stock.getStockId());
        StringBuffer sqlBuffer = new StringBuffer("stock_num = ");
        sqlBuffer.append(stock.getStockNum());
        updateWrapper.setSql(sqlBuffer.toString());
        return this.update(updateWrapper);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        return this.removeByIds(idList);
    }

    @Override
    public List<StockItem> outList() {
        return stockMapper.outList();
    }
}
