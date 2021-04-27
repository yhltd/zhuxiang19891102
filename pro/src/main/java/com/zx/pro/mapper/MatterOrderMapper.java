package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.MatterOrder;
import com.zx.pro.entity.MatterOrderItem;
import com.zx.pro.entity.MatterProject;
import com.zx.pro.entity.MatterProjectChangeItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MatterOrderMapper extends BaseMapper<MatterOrder> {

    @Select("select pi.project_name,mi.code,mi.material_description,mpc.* from matter_project_change as mpc " +
            "left join matter_project as mp on mpc.matter_project_id = mp.id " +
            "left join project_info as pi on mp.project_info_id = pi.id " +
            "left join matter_info as mi " +
            "on mp.matter_info_id = mi.id " +
            "order by mpc.update_time desc")
    List<MatterProjectChangeItem> getList();

    @Select("select mo.id as uid,mo.order_id,mo.num,mi.* " +
            "from matter_order as mo " +
            "left join matter_info as mi " +
            "on mo.matter_id = mi.id")
    List<MatterOrderItem> postList();

    @Select("select mo.id as uid,mo.order_id,mo.num,mi.* " +
            "from matter_order as mo " +
            "left join matter_info as mi " +
            "on mo.matter_id = mi.id " +
            "where mo.order_id like CONCAT('%',#{orderId},'%') " +
            "and mi.code like CONCAT('%',#{code},'%')")
    List<MatterOrderItem> selectList(String orderId, String code);
}
