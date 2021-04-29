package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.MatterInfoItem;
import com.zx.pro.entity.MatterProject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MatterInfoMapper extends BaseMapper<MatterInfo> {

    /**
     * 查询一个项目下所有物料信息
     *
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
     *
     * @param projectInfoId 项目id
     * @return
     */
    @Select("select * from " +
            "(select mi.*," +
            "(select sum(matter_num) as matter_num " +
            "from matter_project as mp " +
            "where mp.matter_info_id = mi.id) " +
            "- (select ifnull(sum(mo.num),0) as use_num " +
            "from matter_order as mo " +
            "where mo.order_id in " +
            "(select order_id " +
            "from order_info as oi " +
            "where oi.project_info_id = #{projectInfoId}) " +
            "and mo.matter_id = mi.id) as matter_num " +
            "from matter_info as mi) as matter_info " +
            "where matter_info.matter_num > 0")
    List<MatterInfoItem> getListOfUse(Integer projectInfoId);


    /**
     * 查询一个产品所需的物料信息
     *
     * @return
     */
    @Select("select mi.*,pm.num,pm.price,pm.matter_project_id " +
            "from product_matter as pm " +
            "left join matter_project as mp " +
            "on pm.matter_project_id = mp.id " +
            "left join matter_info as mi " +
            "on mp.matter_info_id = mi.id ")
    List<MatterInfoItem> getListByProductId();


    /**
     * 查询一个项目下所有物料信息
     *
     * @param projectName 项目名
     * @return
     */
    @Select("select mi.*,mp.project_info_id from matter_info mi " +
            "LEFT JOIN matter_project mp " +
            "on mi.id=mp.matter_info_id " +
            "LEFT JOIN project_info pi " +
            "on pi.id=mp.project_info_id " +
            "where pi.project_name " +
            "like CONCAT('%',#{projectName},'%') ")
    List<MatterInfoItem> getListByProjectName(String projectName);

    /**
     * 删除物料信息
     * @param id
     */
    @Delete("delete from matter_order where matter_id = #{id};" +
            "delete from matter_order_change where matter_info_id = #{id};" +
            "delete from matter_project_change where matter_info_id in (select id from matter_project where matter_info_id = #{id});" +
            "delete from matter_project where matter_info_id = #{id};" +
            "delete from out_stock_detail where matter_id = #{id};" +
            "delete from set_stock_detail where product_info_id = #{id};" +
            "delete from stock where matter_id = #{id};" +
            "delete from work_order_detail where matter_id = #{id};")
    void deleteMatterById(int id);
}
