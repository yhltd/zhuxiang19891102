package com.zx.pro.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("matter_orders")
public class MatterOrder {
    private  int id;
    private  int matter_id;
    private String order_id;
    private int num;
}
