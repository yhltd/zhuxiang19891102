package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.MatterInfoItem;
import com.zx.pro.entity.MatterOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MatterOrderMapper extends BaseMapper<MatterOrder> {

    @Select("select mi.*,mo.matter_num,mo.matter_price from matter_order as mo left join matter_info as mi on mo.matter_info_id = mi.id where mo.order_info_id = #{orderId}")
    List<MatterInfoItem> getMatterInfoItemListByOrderId(int orderId);

    @Select("select mi.*,mo.matter_num,mo.matter_price from matter_order as mo left join matter_info as mi on mo.matter_info_id = mi.id where mo.project_info_id = #{projectId}")
    List<MatterInfoItem> getMatterInfoItemListByProjectId(int projectId);


}
