package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.MatterProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatterProjectMapper extends BaseMapper<MatterProject> {
    /**
     * 查询matter_project表中该项目下是否有该物料
     *
     * @param projectInfoId 项目id
     * @param matterId 物料id
     * @return
     */
//    @Select("select mi.*,mp.matter_num from matter_project as mp " +
//            "left join matter_info as mi " +
//            "on mp.matter_info_id = mi.id " +
//            "where mp.project_info_id = #{projectInfoId}")
    @Select("select count(matter_info_id) from matter_project where project_info_id = #{projectInfoId} and matter_info_id = #{matterId}")
    int getmatterCount(Integer projectInfoId, Integer matterId);

    /**
     * 修改matter_project
     * @param  projectInfoId 项目ID
     * @param matterId 物料代码
     * @param matterNun 物料数量
     */
    @Select("update matter_project SET matter_num = #{matterNun} where project_info_id = #{projectInfoId} and matter_info_id = #{matterId}")
    void updateMatterNum(Integer projectInfoId,Integer matterId,Double matterNun);

    /**
     * matter_project表中新增一条数据
     * @param  projectInfoId 项目ID
     * @param matterId 物料代码
     * @param matterNun 物料数量
     */
    @Select("insert into matter_project(project_info_id,matter_info_id,matter_num) values(#{projectInfoId},#{matterId},#{matterNun})")
    void insertMatterNum(Integer projectInfoId,Integer matterId,Double matterNun);

}
