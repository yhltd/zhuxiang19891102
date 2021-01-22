package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zx.pro.util.StringUtils;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author dai
 */
@Data
@TableName("order_info")
public class OrderInfo {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 贸易条款
     */
    private String comment;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 所属项目id
     */
    private int projectInfoId;
    /**
     * 订单状态
     */
    private String state;
}
