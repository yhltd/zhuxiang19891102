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
    @Select("select ssd.*,pi.* from set_stock_detail as ssd " +
            "left join product_info as pi " +
            "on ssd.product_info_id = pi.id")
    List<SetStockDetail> getList();

    /**
     * 条件查询入库明细
     * @param setOrder 入库单号
     * @param productName 产品名称
     * @return
     */
    @Select("select ssd.*,pi.* from set_stock_detail as ssd " +
            "left join product_info as pi " +
            "on ssd.product_info_id = pi.id " +
            "where ssd.set_order like CONCAT('%',#{setOrder},'%') " +
            "and pi.product_name like CONCAT('%',#{productName},'%')")
    List<SetStockDetail> selectList(String setOrder,String productName);
}
