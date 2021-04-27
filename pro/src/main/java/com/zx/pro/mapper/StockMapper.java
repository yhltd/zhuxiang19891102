package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.Stock;
import com.zx.pro.entity.StockItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dai
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {

    @Select("select m.code,m.type,m.material_description,s.* from stock as s left join matter_info as m on s.matter_id = m.id")
    List<StockItem> getList();

    @Select("select m.code,m.type,m.material_description,s.* from stock as s left join matter_info as m on s.matter_id = m.id " +
            "where m.code like CONCAT('%',#{code},'%')")
    List<StockItem> selectList(String code);

    @Select("select s.*,mi.* from stock as s left join matter_info as mi on s.matter_id = mi.id")
    List<StockItem> outList();
}
