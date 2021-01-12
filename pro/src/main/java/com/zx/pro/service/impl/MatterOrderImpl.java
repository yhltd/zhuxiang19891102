package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterInfoItem;
import com.zx.pro.entity.MatterOrder;
import com.zx.pro.mapper.MatterOrderMapper;
import com.zx.pro.service.IMatterOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实现物料订单表接口的增删改查
 * @author wanghui
 */
@Service
public class MatterOrderImpl extends ServiceImpl<MatterOrderMapper,MatterOrder> implements IMatterOrderService {

    @Resource
    private MatterOrderMapper matterOrderMapper;

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
    public boolean addMatterOrder(MatterOrder matterOrder) {
        return this.save(matterOrder);
    }

    @Override
    public boolean deleteMatterOrder(int id) {
        return this.removeById(id);
    }

    public List<MatterInfoItem> getMatterByOrderId(int orderId) {
        List<MatterInfoItem> matterInfoItemList = matterOrderMapper.getMatterInfoItemListByOrderId(orderId);
        return matterInfoItemList;
    }

    public List<MatterInfoItem> getMatterByProjectId(int projectId) {
        List<MatterInfoItem> matterInfoItemList = matterOrderMapper.getMatterInfoItemListByOrderId(projectId);
        return matterInfoItemList;
    }
}
