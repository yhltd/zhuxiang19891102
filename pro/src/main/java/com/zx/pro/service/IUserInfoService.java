package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.UserInfo;
import org.springframework.stereotype.Service;

/**
 * 用户Service
 *
 * @author dai
 */
@Service
public interface IUserInfoService extends IService<UserInfo> {

    String login(String name,String pwd);
}
