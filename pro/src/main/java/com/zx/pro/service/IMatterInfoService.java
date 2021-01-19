package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.MatterInfoItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 物料表接口
 * @author dai
 */
@Service
public interface IMatterInfoService extends IService<MatterInfo> {

    /**
     * 查询物料集合
     * @return 物料信息的集合
     */
    List<MatterInfo> getList();

    /**
     * 查询物料集合
     * @param code 物料代码
     * @return 物料信息的集合
     */
    List<MatterInfo> getList(String code);

    /**
     * 根据项目id查询所需物料信息
     * @param projectId 项目id
     * @return 带有数量和单价的物料信息
     */
    List<MatterInfoItem> getList(Integer projectId);

    /**
     * 修改
     * @param matterInfo 修改过的物料对象
     * @return 是否修改成功
     */
    boolean update(MatterInfo matterInfo);

    /**
     * 添加物料
     * @param matterInfo 添加物料的对象
     * @return 是否添加成功
     */
    MatterInfo add(MatterInfo matterInfo);

    /**
     * 删除物料
     * @param id 根据id删除
     * @return 是否删除成功
     */
    boolean delete(int id);

    /**
     * 删除物料
     * @param idList id集合
     * @return 是否删除成功
     */
    boolean delete(List<Integer> idList);
}
