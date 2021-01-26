package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.UserPower;
import com.zx.pro.mapper.UserPowerMapper;
import com.zx.pro.service.IUserPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanghui
 * @date 2021/1/24 11:56
 */
@Service
public class UserPowerImpl extends ServiceImpl<UserPowerMapper, UserPower> implements IUserPowerService {

    @Autowired
    private UserPowerMapper userPowerMapper;

    @Override
    public List<UserPower> getList(int id) {
        return this.userPowerMapper.getList(id);
    }

    @Override
    public UserPower add(UserPower userPower) {
        return this.save(userPower) ? userPower : null;
    }

    @Override
    public boolean update(UserPower userPower) {
        return this.updateById(userPower);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        return removeByIds(idList);
    }

}
