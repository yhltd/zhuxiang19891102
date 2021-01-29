package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dai
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {

    @Select("select pi.product_name,pi.product_price,s.* " +
            "from stock as s " +
            "left join product_info as pi " +
            "on s.product_info_id = pi.id")
    List<Stock> getList();

    @Select("select pi.product_name,pi.product_price,s.* " +
            "from stock as s " +
            "left join product_info as pi " +
            "on s.product_info_id = pi.id " +
            "where pi.product_name like CONCAT('%',#{productName},'%')")
    List<Stock> selectList(String productName);
}
