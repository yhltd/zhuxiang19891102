package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterProduct;
import com.zx.pro.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 物料订单表接口
 *
 * @author dai
 */
@Service
public interface IMatterProductService extends IService<MatterProduct> {
    /**
     * 查询物料订单表
     * @return 物料订单表信息的集合
     */
    List<MatterProduct> getList();

    /**
     * 获取id集合
     * @param projectInfoId 项目id
     * @return id集合
     */
    List<MatterProduct> getList(int projectInfoId);

    /**
     * 修改物料订单表
     * @param matterProduct 修改过的物料订单表对象
     * @return 是否修改成功
     */
    boolean update(MatterProduct matterProduct);

    /**
     * 批量修改
     * @param matterProduct 需要修改的集合
     * @return 是否修改成功
     */
    boolean update(List<MatterProduct> matterProduct);

    /**
     * 添加物料订单
     * @param matterProduct 添加物料订单表对象
     * @return 是否添加成功
     */
    MatterProduct add(MatterProduct matterProduct);

    /**
     * 批量添加物料订单
     * @param list
     * @return
     */
    boolean add(List<MatterProduct> list);

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
    boolean change(List<MatterProduct> newMatterProductList, Integer projectInfoId, UserInfo userInfo);
}
