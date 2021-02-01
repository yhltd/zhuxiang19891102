package com.zx.pro.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author dai
 */
@Data
public class OrderState{

    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 订单数量
     */
    private Double orderNum;
    /**
     * 开始生产日期
     */
    private Date workDate;
    /**
     * 第一次入库日期
     */
    private LocalDateTime setStockDate;
    /**
     * 入库数量
     */
    private Double setNum;
    /**
     * 第一次出库日期
     */
    private LocalDateTime outStockDate;
    /**
     * 出库数量
     */
    private Double outkNum;
}
