package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 *
 * @author dai
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
