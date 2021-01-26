package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户接口
 *
 * @author dai
 */
@Service
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 登陆
     *
     * @param name 用户名
     * @param pwd  密码
     * @return 转Json后的用户信息
     */
    Map<String,Object> login(String name, String pwd);

    /**
     * 查询
     *
     * @return user信息集合
     */
    List<UserInfo> getList();

    /**
     * 添加
     *
     * @param userInfo 添加user对象
     * @return 是否添加成功
     */
    UserInfo add(UserInfo userInfo);

    /**
     * 修改
     *
     * @param userInfo 修改user对象
     * @return 是否修改成功
     */
    boolean update(UserInfo userInfo);

    /**
     * 删除
     *
     * @param idList 根据id集合删除
     * @return 是否删除成功
     */
    boolean delete(List<Integer> idList);


    /**
     * 修改密码
     * @param pwd 密码
     * @param userInfo 用户名和旧密码
     * @return
     */
    boolean updatePwd(String pwd,UserInfo userInfo);
}
