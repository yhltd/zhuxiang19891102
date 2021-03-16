package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.OutStockDetail;
import com.zx.pro.entity.SetStockDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author dai
 */
@Mapper
public interface OutStockDetailMapper extends BaseMapper<OutStockDetail>{

    /**
     * 查询出库明细
     * @return
     */
    @Select("select osd.*,m.code as mattername from out_stock_detail as osd left join matter_info as m on osd.matter_id = m.id")
    List<OutStockDetail> getList();

    /**
     * 条件查询出库明细
     * @param outOrder 出库单号
     * @param productName 产品名称
     * @return
     */
    @Select("select osd.*,m.code as mattername from out_stock_detail as osd left join matter_info as m on osd.matter_id = m.id " +
            "where osd.out_order like '%${outOrder}%' and m.code like '%${productName}%'")
    List<OutStockDetail> selectList(String outOrder,String productName);


}
