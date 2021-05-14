package com.zx.pro.entity;

import lombok.Data;

/**
 * @author dai
 */
@Data
public class OrderInfoItem extends OrderInfo{

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 出库数量
     */
    private double outNum;
}
