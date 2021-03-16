package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.SetStockDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dai
 */
@Mapper
public interface SetStockDetailMapper extends BaseMapper<SetStockDetail>{

    /**
     * 查询入库明细
     * @return
     */
    @Select("select ssd.*,m.code as mattername from set_stock_detail as ssd left join matter_info as m on ssd.matter_id = m.id")
    List<SetStockDetail> getList();

    /**
     * 条件查询入库明细
     * @param setOrder 入库单号
     * @param productName 产品名称
     * @return
     */
    @Select("select ssd.*,m.code as mattername from set_stock_detail as ssd " +
            "left join matter_info as m " +
            "on ssd.matter_id = m.id " +
            "where ssd.set_order like '%${setOrder}%' " +
            "and m.code like '%${productName}%'")
    List<SetStockDetail> selectList(String setOrder,String productName);
}
