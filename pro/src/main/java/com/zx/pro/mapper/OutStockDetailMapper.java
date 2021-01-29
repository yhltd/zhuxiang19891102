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
    @Select("select osd.*,pi.* " +
            "from out_stock_detail as osd " +
            "left join product_info as pi " +
            "on osd.product_info_id = pi.id")
    List<OutStockDetail> getList();

    /**
     * 条件查询出库明细
     * @param outOrder 出库单号
     * @param productName 产品名称
     * @return
     */
    @Select("select ssd.*,pi.* from set_stock_detail as ssd " +
            "left join product_info as pi " +
            "on ssd.product_info_id = pi.id " +
            "where osd.out_order like CONCAT('%',#{outOrder},'%') " +
            "and pi.product_name like CONCAT('%',#{productName},'%')")
    List<OutStockDetail> selectList(String outOrder,String productName);


}
