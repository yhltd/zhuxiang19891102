package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户Mapper
 *
 * @author dai
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    @Select("update user_info set pwd=#{pwd} where name=#{userInfo.name} and pwd=#{userInfo.pwd}")
    boolean updatePwd(String pwd, UserInfo userInfo);

}
