package com.zx.pro.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.UserInfo;
import com.zx.pro.mapper.MatterInfoMapper;
import com.zx.pro.service.IMatterInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现物料表接口的增删改查
 *
 * @author wanghui
 */
@Service
public class MatterInfoImpl extends ServiceImpl<MatterInfoMapper, MatterInfo> implements IMatterInfoService {

    @Override
    public List<MatterInfo> getList() {
        return this.list();
    }

    @Override
    public boolean update(MatterInfo matterInfo) {
        UpdateWrapper<MatterInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",matterInfo.getId());
        return this.update(matterInfo,updateWrapper);
    }

    @Override
    public boolean add(MatterInfo matterInfo) {
        return this.save(matterInfo);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public boolean delete(List<Integer>id) {
        return this.removeByIds(id);
    }

}
