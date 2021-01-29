package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.UserInfo;
import com.zx.pro.entity.UserPower;
import com.zx.pro.mapper.UserInfoMapper;
import com.zx.pro.service.IUserInfoService;
import com.zx.pro.service.IUserPowerService;
import com.zx.pro.util.GsonUtil;
import com.zx.pro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户接口实现类
 *
 * @author dai
 */
@Service
public class UserInfoImpl extends ServiceImpl<UserInfoMapper,UserInfo> implements IUserInfoService {

    @Autowired
    private IUserPowerService iUserPowerService;

    @Autowired UserInfoMapper userInfoMapper;

    @Override
    public Map<String,Object> login(String name, String pwd) {
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

            List<UserPower> powerList = iUserPowerService.getList(userInfo.getId());
            Map<String,Object> map = new HashMap<>();
            map.put("token",data);
            map.put("power",powerList);
            return map;
        }
        return null;
    }

    @Override
    public List<UserInfo> getList() {
        return this.list();
    }

    @Override
    public UserInfo add(UserInfo userInfo) {
        return this.save(userInfo) ? userInfo : null;
    }


    @Override
    public boolean update(UserInfo userInfo) {
        return this.updateById(userInfo);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        return removeByIds(idList);
    }
}
