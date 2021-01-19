package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("work_order_info")
public class WorkOrderInfo {
    /**
     * 主键
     */
    @TableId(value = "id" , type = IdType.AUTO)
    private int id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 派工单单号
     */
    private String workOrder;

    /**
     * 状态
     */
    private String state;
}
