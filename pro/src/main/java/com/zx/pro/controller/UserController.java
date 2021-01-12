package com.zx.pro.controller;

import com.zx.pro.service.IUserInfoService;
import com.zx.pro.util.ResultInfo;
import com.zx.pro.util.SessionUtil;
import com.zx.pro.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 用户控制器
 *
 * @author dai
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * Service层实例
     */
    @Resource
    private IUserInfoService iUserInfoService;

    /**
     * 登陆接口
     *
     * @param name 用户名
     * @param pwd  密码
     * @return ResultInfo（只有msg）
     */
    @PostMapping("/login")
    public ResultInfo login(HttpSession session, String name, String pwd) {
        log.debug("name:{}",name);
        log.debug("pwd:{}",pwd);
        //user的json对象
        String data = StringUtils.EMPTY;
        try {
            //获取user
            data = iUserInfoService.login(name, pwd);
        } catch (Exception ex) {
            return ResultInfo.error("错误!");
        }
        //为Null则查询不到
        if (StringUtils.isEmpty(data)) {
            SessionUtil.remove(session,"token");
            return ResultInfo.error(-1,"用户名密码错误");
        } else {
            SessionUtil.setToken(session,data);
            return ResultInfo.success("登陆成功", null);
        }
    }
}
