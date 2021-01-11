package com.zx.pro.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterInfo;
import com.zx.pro.mapper.MatterInfoMapper;
import com.zx.pro.service.IMatterInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatterInfoImpl extends ServiceImpl<MatterInfoMapper, MatterInfo> implements IMatterInfoService {

    @Override
    public List<MatterInfo> getMatterInfoList() {
        return this.list();
    }

    @Override
    public boolean updateMatterInfo(MatterInfo matterInfo) {
        UpdateWrapper<MatterInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",matterInfo.getId());
        return this.update(matterInfo,updateWrapper);
    }

    @Override
    public boolean insertMatterInfo(MatterInfo matterInfo) {
        return this.save(matterInfo);
    }

    @Override
    public boolean deleteMatterInfo(int id) {
        return this.removeById(id);
    }


}
