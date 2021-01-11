package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserInfoService extends IService<UserInfo> {

    List<UserInfo> getUserInfoList();
}
