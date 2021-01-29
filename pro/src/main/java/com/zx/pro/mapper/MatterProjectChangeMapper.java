package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zx.pro.entity.MatterProjectChange;
import com.zx.pro.entity.MatterProjectChangeItem;
import com.zx.pro.entity.ProductInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dai
 */
@Mapper
public interface MatterProjectChangeMapper extends BaseMapper<MatterProjectChange> {

    @Select("select pi.project_name,mi.code,mpc.* from matter_project_change as mpc " +
            "left join matter_project as mp on mpc.matter_project_id = mp.id " +
            "left join project_info as pi on mp.project_info_id = pi.id " +
            "left join matter_info as mi " +
            "on mp.matter_info_id = mi.id " +
            "order by mpc.update_time desc")
    List<MatterProjectChangeItem> getList();

    /**
     * 按条件查询
     *
     * @param projectName 项目名称
     * @param code        物料编码
     * @param updateMan   修改人
     * @return
     */
    @Select("select pi.project_name,mi.code,mpc.* from matter_project_change as mpc " +
            "left join matter_project as mp on mpc.matter_project_id = mp.id " +
            "left join project_info as pi on mp.project_info_id = pi.id " +
            "left join matter_info as mi on mp.matter_info_id = mi.id " +
            "where pi.project_name like concat('%',#{projectName},'%') " +
            "and mi.code like concat('%',#{code},'%') " +
            "and mpc.update_man like concat('%',#{updateMan},'%') " +
            "order by mpc.update_time desc")
    List<MatterProjectChangeItem> selectList(@Param("projectName") String projectName,
                                             @Param("code") String code,
                                             @Param("updateMan") String updateMan);
}
