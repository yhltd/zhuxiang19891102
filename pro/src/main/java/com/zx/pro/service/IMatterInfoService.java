package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物料表接口
 * @author wanghui
 */
@Service
public interface IMatterInfoService extends IService<MatterInfo> {

    /**
     * 查询物料信息
     * @return 物料信息的集合
     */
    List<MatterInfo> getList();

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
    boolean add(MatterInfo matterInfo);

    /**
     * 删除物料
     * @param id 根据id删除
     * @return 是否删除成功
     */
    boolean delete(int id);

}
