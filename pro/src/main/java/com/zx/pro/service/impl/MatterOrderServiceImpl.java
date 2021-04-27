package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterOrder;
import com.zx.pro.entity.MatterOrderChange;
import com.zx.pro.entity.MatterOrderItem;
import com.zx.pro.entity.UserInfo;
import com.zx.pro.mapper.MatterOrderMapper;
import com.zx.pro.service.IMatterOrderChangeService;
import com.zx.pro.service.IMatterOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatterOrderServiceImpl extends ServiceImpl<MatterOrderMapper, MatterOrder> implements IMatterOrderService {

    @Autowired
    private MatterOrderMapper matterOrderMapper;

    @Autowired
    private IMatterOrderChangeService iMatterOrderChangeService;

    @Override
    public List<MatterOrderItem> postList() {
        return matterOrderMapper.postList();
    }

    @Override
    public List<MatterOrderItem> selectList(String orderId, String code) {
        return matterOrderMapper.selectList(orderId, code);
    }

    @Override
    public boolean update(UserInfo user,int id, String orderId, int uid, int oldNum, int newNum) {
        UpdateWrapper<MatterOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("num",newNum);
        updateWrapper.eq("id",uid);
        if(this.update(updateWrapper)){
            MatterOrderChange matterOrderChange = new MatterOrderChange();
            matterOrderChange.setOrderId(orderId);
            matterOrderChange.setMatterInfoId(id);
            matterOrderChange.setNewNum(newNum);
            matterOrderChange.setOldNum(oldNum);
            matterOrderChange.setUpdateTime(LocalDateTime.now());
            matterOrderChange.setUpdateMan(user.getName());
            return iMatterOrderChangeService.add(matterOrderChange);
        }
        return false;
    }

    @Override
    public boolean add(int id) {
        return this.removeById(id);
    }

    @Override
    public boolean add(List<MatterOrder> list) {
        return this.saveBatch(list, 50);
    }

    @Override
    public boolean delete(List<Integer> idList){
        return this.removeByIds(idList);
    }

    @Override
    public boolean deleteByOrderList(List<String> orderList) {
        QueryWrapper<MatterOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("order_id", orderList);
        return this.remove(queryWrapper);
    }

    @Override
    public boolean deleteByOrderId(String orderId) {
        QueryWrapper<MatterOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        return this.remove(queryWrapper);
    }
}
