package com.zx.pro.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author dai
 */
@Data
public class OrderState extends OrderInfoItem{

    /**
     * 订单数量
     */
    private Double orderNum;
    /**
     * 开始生产日期
     */
    private Date workDate;
    /**
     * 生产数量
     */
    private Double workNum;
//    /**
//     * 第一次入库时间
//     */
//    private LocalDateTime setStockDate;
//    /**
//     * 入库数量
//     */
//    private Double setStockNum;
//    /**
//     * 第一次出库日期
//     */
//    private LocalDateTime outStockDate;
//    /**
//     * 出库数量
//     */
//    private Double outStockNum;
}
