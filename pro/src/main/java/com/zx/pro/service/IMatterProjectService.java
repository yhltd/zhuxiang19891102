package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterProject;
import com.zx.pro.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物料订单表接口
 *
 * @author dai
 */
@Service
public interface IMatterProjectService extends IService<MatterProject> {
    /**
     * 查询物料订单表
     * @return 物料订单表信息的集合
     */
    List<MatterProject> getList();

    /**
     * 获取id集合
     * @param projectInfoId 项目id
     * @return id集合
     */
    List<MatterProject> getList(int projectInfoId);

    /**
     * 获取id集合
     * @param projectInfoIdList 项目id集合
     * @return id集合
     */
    List<MatterProject> getList(List<Integer> projectInfoIdList);

    /**
     * 修改物料订单表
     * @param matterProduct 修改过的物料订单表对象
     * @return 是否修改成功
     */
    boolean update(MatterProject matterProduct);

    /**
     * 批量修改
     * @param matterProduct 需要修改的集合
     * @return 是否修改成功
     */
    boolean update(List<MatterProject> matterProduct);

    /**
     * 添加物料订单
     * @param matterProduct 添加物料订单表对象
     * @return 是否添加成功
     */
    MatterProject add(MatterProject matterProduct);

    /**
     * 批量添加物料订单
     * @param list
     * @return
     */
    boolean add(List<MatterProject> list);

    /**
     * 删除
     * @param id 根据id删除
     * @return 是否删除成功
     */
    boolean delete(int id);

    /**
     * 批量删除
     * @param idList id集合
     * @return 是否成功
     */
    boolean delete(List<Integer> idList);

    /**
     * 根据项目id批量删除
     * @param idList 项目id集合
     * @return
     */
    boolean deleteByProjectId(List<Integer> idList);

    /**
     * 该表有变动插入项目物料变化表
     * @param newMatterProductList 修改后的项目物料表
     * @param projectInfoId 项目id
     * @param userInfo 当前登陆用户信息
     * @return
     */
    boolean change(List<MatterProject> newMatterProductList, Integer projectInfoId, UserInfo userInfo);
}
