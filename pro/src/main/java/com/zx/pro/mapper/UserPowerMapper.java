package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.UserPower;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wanghui
 * @date 2021/1/24 11:58
 */
@Mapper
public interface UserPowerMapper extends BaseMapper<UserPower> {
    /**
     * 根据用户id查询
     * @return 权限集合
     */
    @Select("select up.* from user_power up LEFT JOIN user_info ui on up.user_info_id=ui.id where ui.id=#{id}")
    List<UserPower>getList(int id);
}
