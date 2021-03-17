package com.zx.pro.entity;

import lombok.Data;

@Data
public class MatterOrderItem extends MatterInfo{

    private int uid;

    private String OrderId;

    private int num;
}
