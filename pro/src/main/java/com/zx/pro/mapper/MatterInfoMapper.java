package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.MatterInfoItem;
import com.zx.pro.entity.MatterProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MatterInfoMapper extends BaseMapper<MatterInfo> {

    /**
     * 查询一个项目下所有物料信息
     * @param projectInfoId 项目id
     * @return
     */
    @Select("select mi.*,mp.matter_num from matter_project as mp " +
            "left join matter_info as mi " +
            "on mp.matter_info_id = mi.id " +
            "where mp.project_info_id = #{projectInfoId}")
    List<MatterInfoItem> getList(Integer projectInfoId);

    /**
     * 查询一个项目下所有可用的物料信息
     * @param projectInfoId 项目id
     * @return
     */
    @Select("select * from " +
            "(select mi.*,mp.id as matterProjectId,mp.matter_num-sum(ifnull(pm.num,0)) as matter_num " +
            "from matter_project as mp " +
            "left join matter_info as mi " +
            "on mp.matter_info_id = mi.id " +
            "left join product_matter as pm " +
            "on mp.id = pm.matter_project_id " +
            "where mp.project_info_id = #{projectInfoId} " +
            "group by mp.matter_info_id) as mi_info " +
            "where mi_info.matter_num > 0")
    List<MatterInfoItem> getListOfUse(Integer projectInfoId);
}
