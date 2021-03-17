package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.MatterOrderChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MatterOrderChangeMapper extends BaseMapper<MatterOrderChange> {

    @Select("select * from matter_order_change")
    List<MatterOrderChange> postList();


    @Select("select moc.* from matter_order_change as moc " +
            "left join matter_info as mi " +
            "on moc.matter_info_id = mi.id " +
            "where mi.code like concat('%',#{code},'%')")
    List<MatterOrderChange> selectList(String code);
}
