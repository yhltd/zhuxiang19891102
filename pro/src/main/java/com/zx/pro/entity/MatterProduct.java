package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("matter_product")
public class MatterProduct {
    /**
     * 主键
     */
    @TableId(value = "id" , type = IdType.AUTO)
    private int id;
    /**
     * 项目id
     */
    private int projectInfoId;
    /**
     * 产品id
     */
    private int productInfoId;
    /**
     * 物料id
     */
    private int matterInfo_id;
    /**
     * 数量
     */
    private int matterNum;
    /**
     * 单价
     */
    private double matterPrice;
}
