package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterOrder;
import com.zx.pro.entity.MatterOrderChange;
import com.zx.pro.mapper.MatterOrderChangeMapper;
import com.zx.pro.service.IMatterOrderChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatterOrderChangeImpl extends ServiceImpl<MatterOrderChangeMapper, MatterOrderChange> implements IMatterOrderChangeService {

    @Autowired
    private MatterOrderChangeMapper matterOrderChangeMapper;

    @Override
    public List<MatterOrderChange> postList() {
        return matterOrderChangeMapper.postList();
    }

    @Override
    public List<MatterOrderChange> selectList(String code) {
        return matterOrderChangeMapper.selectList(code);
    }

    @Override
    public boolean add(MatterOrderChange matterOrderChange) {
        return this.save(matterOrderChange);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        return this.removeByIds(idList);
    }

    @Override
    public boolean update(MatterOrderChange matterOrderChange) {
        return this.updateById(matterOrderChange);
    }
}
