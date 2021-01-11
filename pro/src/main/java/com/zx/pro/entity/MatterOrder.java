package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("matter_order")
public class MatterOrder {
    /**
     * 主键
     */
    @TableId(value = "id" , type = IdType.AUTO)
    private int id;
    private int orderInfoId;
    private int projectInfoId;
    private int matterInfo_id;
    private int matterNum;
    private double matterPrice;
}
