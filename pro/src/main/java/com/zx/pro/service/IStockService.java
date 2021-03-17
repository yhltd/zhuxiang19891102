package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.Stock;
import com.zx.pro.entity.StockItem;
import com.zx.pro.mapper.StockMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 */
@Service
public interface IStockService extends IService<Stock> {

    /**
     * 添加或修改库存
     * @param stock
     * @param isSet true 入库 false 出库
     * @return
     */
    boolean addOrUpdate(Stock stock, Boolean isSet);

    List<StockItem> getList();

    List<StockItem> getList(String code);

    boolean update(Stock stock);

    boolean delete(List<Integer> idList);



    List<StockItem> outList();
}
