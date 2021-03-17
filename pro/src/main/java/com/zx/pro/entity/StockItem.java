package com.zx.pro.entity;

import lombok.Data;

@Data
public class StockItem extends Stock{

    private String code;

    private int outNum;
}
