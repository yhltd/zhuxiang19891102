package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.UserInfo;
import com.zx.pro.mapper.UserInfoMapper;
import com.zx.pro.service.IUserInfoService;
import com.zx.pro.util.GsonUtil;
import com.zx.pro.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 用户Service层实现类
 *
 * @author dai
 */
@Service
public class UserInfoImpl extends ServiceImpl<UserInfoMapper,UserInfo> implements IUserInfoService {

    @Override
    public String login(String name, String pwd) {
        //条件构造器
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        //账号
        queryWrapper.eq("name",name);
        //密码
        queryWrapper.eq("pwd",pwd);
        //获取User
        UserInfo userInfo = this.getOne(queryWrapper);
        //如果不为空
        String data = StringUtils.EMPTY;
        if(StringUtils.isNotNull(userInfo)){
            //转JSON
            data = GsonUtil.toJson(userInfo);
        }
        return data;
    }
}
