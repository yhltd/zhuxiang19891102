package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.UserInfo;
import com.zx.pro.mapper.UserInfoMapper;
import com.zx.pro.service.IUserInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoImpl extends ServiceImpl<UserInfoMapper,UserInfo> implements IUserInfoService {

    @Override
    public List<UserInfo> getUserInfoList() {
        return this.list();
    }
}
