package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterProjectChange;
import com.zx.pro.entity.MatterProjectChangeItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目物料需求变化表Service
 *
 * @author dai
 */
@Service
public interface IMatterProjectChangeService extends IService<MatterProjectChange> {

    /**
     * 获取集合
     * @return 集合
     */
    List<MatterProjectChangeItem> getList();

    /**
     * 按条件查询
     * @param projectName 项目名称
     * @param code 物料编码
     * @param updateMan 修改人
     * @return
     */
    List<MatterProjectChangeItem> getList(String projectName, String code, String updateMan);

    /**
     * 获取集合
     * @param projectId 项目id
     * @return 集合
     */
    List<MatterProjectChange> getList(int projectId);

    /**
     * 添加
     * @param matterProjectChange
     * @return
     */
    MatterProjectChange add(MatterProjectChange matterProjectChange);

    /**
     * 批量添加
     * @param list 添加的集合
     * @return
     */
     boolean add(List<MatterProjectChange> list);

    /**
     * 修改
     * @param matterProjectChange
     * @return
     */
    boolean update(MatterProjectChange matterProjectChange);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean delete(int id);

    /**
     * 批量删除
     * @param idList
     * @return
     */
    boolean delete(List<Integer> idList);

    /**
     * 根据项目物料表id删除
     * @param matterProjecttIdList 项目物料表id集合
     * @return
     */
    boolean deleteByMatterProjectId(List<Integer> matterProjecttIdList);
}
