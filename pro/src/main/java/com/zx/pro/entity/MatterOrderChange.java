package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("matter_order_change")
public class MatterOrderChange {

    @TableId(value="id", type= IdType.AUTO)
    private int id;

    private int matterInfoId;

    private String orderId;

    private int oldNum;

    private int newNum;

    private LocalDateTime updateTime;

    private String updateMan;
}
