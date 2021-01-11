package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.MatterOrder;
import com.zx.pro.mapper.MatterInfoMapper;
import com.zx.pro.mapper.MatterOrderMapper;
import com.zx.pro.service.IMatterOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatterOrderImpl extends ServiceImpl<MatterOrderMapper,MatterOrder> implements IMatterOrderService {
    @Override
    public List<MatterOrder> getMatterOrder() {
        return this.list();
    }

    @Override
    public boolean updateMatterOrder(MatterOrder matterOrder) {
        UpdateWrapper<MatterOrder> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",matterOrder.getId());
        return this.update(matterOrder,updateWrapper);
    }

    @Override
    public boolean insertMatterOrder(MatterOrder matterOrder) {
        return this.save(matterOrder);
    }

    @Override
    public boolean deleteMatterOrder(int id) {
        return this.removeById(id);
    }
}
