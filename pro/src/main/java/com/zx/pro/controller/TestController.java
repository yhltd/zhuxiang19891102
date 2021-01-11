package com.zx.pro.controller;

import com.zx.pro.entity.UserInfo;
import com.zx.pro.service.IUserInfoService;
import com.zx.pro.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class TestController {

    @Resource
    private IUserInfoService iUserInfoService;

    @RequestMapping("/getList")
    public ResultInfo getUserInfoList(){
        List<UserInfo> userInfoList = null;
        try{
            userInfoList = iUserInfoService.getUserInfoList();
            return ResultInfo.success("获取成功",userInfoList);
        }catch(Exception ex){
            return ResultInfo.error("错误!");
        }
    }
}
