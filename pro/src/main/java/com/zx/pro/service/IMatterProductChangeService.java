package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterProductChange;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目物料需求变化表Service
 *
 * @author dai
 */
@Service
public interface IMatterProductChangeService extends IService<MatterProductChange> {

    /**
     * 获取集合
     * @return 集合
     */
    List<MatterProductChange> getList();

    /**
     * 获取集合
     * @param projectId 项目id
     * @return 集合
     */
    List<MatterProductChange> getList(int projectId);

    /**
     * 添加
     * @param matterProductChange
     * @return
     */
    MatterProductChange add(MatterProductChange matterProductChange);

    /**
     * 批量添加
     * @param list 添加的集合
     * @return
     */
     boolean add(List<MatterProductChange> list);

    /**
     * 修改
     * @param matterProductChange
     * @return
     */
    boolean update(MatterProductChange matterProductChange);

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
}
