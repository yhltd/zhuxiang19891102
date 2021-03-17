package com.zx.pro.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("matter_order")
public class MatterOrder {

    @TableId(value = "id" , type = IdType.AUTO)
    private int id;

    /**
     * 物料id
     */
    private int matterId;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 数量
     */
    private int num;
}
