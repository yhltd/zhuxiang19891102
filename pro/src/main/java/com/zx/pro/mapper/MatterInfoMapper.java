package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.MatterInfoItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MatterInfoMapper extends BaseMapper<MatterInfo> {

    @Select("select mp.matter_num,mp.matter_price,mi.* from matter_product as mp left join matter_info as mi on mp.matter_info_id = mi.id where mp.project_info_id = #{projectInfoId}")
    List<MatterInfoItem> getList(Integer projectInfoId);
}
