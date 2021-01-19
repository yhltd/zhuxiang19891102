package com.zx.pro.entity;

import lombok.Data;

/**
 * @author wanghui
 * @date 2021/1/18 16:51
 */
@Data
public class WorkOrderDetailItem extends WorkOrderDetail{

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 派工单单号
     */
    private String workOrder;
}
