package com.zx.pro.entity;

import lombok.Data;

@Data
public class StockItem extends Stock{

    private String code;

    private String type;

    private String materialDescription;

    private int outNum;
}
