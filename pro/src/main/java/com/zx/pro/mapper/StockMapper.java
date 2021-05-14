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

//    @Select("select code,stock_num from order_info oi left join matter_project mp on mp.project_info_id=oi.project_info_id left join matter_info mi on mi.id=mp.matter_info_id LEFT JOIN stock st on st.matter_id=mi.id where order_id=#{orderInfoId}")
//    List<StockItem>outList(String orderInfoId);
}
