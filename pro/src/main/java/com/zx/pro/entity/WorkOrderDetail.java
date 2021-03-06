package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("work_order_detail")
public class WorkOrderDetail {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    /**
     * 派工单信息表id
     */
    private int workOrderInfoId;

    /**
     * 物料信息表id
     */

    private int matterId;

    /**
     * 生产时间
     */
    private Date workDate;

    /**
     * 车间
     */
    private String workShop;

    /**
     * 产线
     */
    private String workLine;

    /**
     * 数量
     */
    private int workNum;

    /**
     * 订单号
     */
    private String orderInfoId;
}
