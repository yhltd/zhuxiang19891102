package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.UserInfo;
import org.springframework.stereotype.Service;

/**
 * 用户接口
 *
 * @author dai
 */
@Service
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 登陆
     * @param name 用户名
     * @param pwd 密码
     * @return 转Json后的用户信息
     */
    String login(String name,String pwd);
}
