package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.UserPower;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanghui
 * @date 2021/1/23 15:04
 */
@Service
public interface IUserPowerService extends IService<UserPower> {

    /**
     * 查询
     * @return 权限的集合
     */
    List<UserPower> getList(int id);

    /**
     * 添加
     * @param userPower 权限对象
     * @return 是否添加成功
     */
    UserPower add(UserPower userPower);

    /**
     * 修改
     * @param userPower 权限对象
     * @return 是否修改成功
     */
    boolean update(UserPower userPower);

    /**
     * 删除
     * @param idList id集合
     * @return 是否删除成功
     */
    boolean delete(List<Integer>idList);

}
