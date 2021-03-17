package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterOrder;
import com.zx.pro.entity.MatterOrderChange;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMatterOrderChangeService extends IService<MatterOrderChange> {

    /**
     * 查询所有数据
     * @return
     */
    List<MatterOrderChange> postList();

    /**
     * 按条件查询
     * @param code 物料编码
     * @return
     */
    List<MatterOrderChange> selectList(String code);

    /**
     * 添加一条
     * @param matterOrderChange
     * @return
     */
    boolean add(MatterOrderChange matterOrderChange);

    /**
     * 批量删除
     * @param idList id集合
     * @return
     */
    boolean delete(List<Integer> idList);

    /**
     * 修改
     * @param matterOrderChange
     * @return
     */
    boolean update(MatterOrderChange matterOrderChange);
}
