package com.zx.pro.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.MatterInfoItem;
import com.zx.pro.mapper.MatterInfoMapper;
import com.zx.pro.service.IMatterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现物料表接口的增删改查
 *
 * @author dai
 */
@Service
public class MatterInfoImpl extends ServiceImpl<MatterInfoMapper, MatterInfo> implements IMatterInfoService {

    @Autowired
    private MatterInfoMapper matterInfoMapper;

    @Override
    public List<MatterInfo> getList() {
        return this.list();
    }

    @Override
    public List<MatterInfo> getList(String code) {
        QueryWrapper<MatterInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("code",code);
        return this.list(queryWrapper);
    }

    @Override
    public List<MatterInfoItem> getList(Integer projectId) {
        return matterInfoMapper.getList(projectId);
    }

    @Override
    public List<MatterInfoItem> getListByProductId() {
        return matterInfoMapper.getListByProductId();
    }

    @Override
    public List<MatterInfoItem> getListOfUse(Integer projectId) {
        return matterInfoMapper.getListOfUse(projectId);
    }

    @Override
    public List<MatterInfoItem> getListByProjectName(String projectName) {
        return matterInfoMapper.getListByProjectName(projectName);
    }

    @Override
    public boolean update(MatterInfo matterInfo) {
        return this.updateById(matterInfo);
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
    public boolean delete(List<Integer> idList) {
        return this.removeByIds(idList);
    }

}
