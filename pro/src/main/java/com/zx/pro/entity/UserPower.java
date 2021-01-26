package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wanghui
 * @date 2021/1/23 14:51
 */
@Data
@TableName("user_power")
public class UserPower {
    /**
     * id自增列
     */
    @TableId(value = "id" , type = IdType.AUTO)
    private int id;

    /**
     * 用户信息表id
     */
    private int userInfoId;

    /**
     * 表名
     */
    private String viewName;

    /**
     * 增
     */
    private int adds;

    /**
     * 删
     */
    private int deletes;

    /**
     * 改
     */
    private int updates;

    /**
     * 查
     */
    private int selects;


}
