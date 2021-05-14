package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author dai
 */
@Data
@TableName("out_stock_detail")
public class OutStockDetail {

    /**
     * 主键id
     */
    @TableId(value = "out_stock_id", type = IdType.AUTO)
    private int outStockId;

    /**
     * 产品信息id
     */
    private int matterId;
    /**
     * 出库单号
     */
    private String outOrder;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 出库数量
     */
    private Double outNum;
    /**
     * 出库人
     */
    private String man;
    /**
     * 出库地址
     */
    private String outAddress;
    /**
     * 产品名称
     */
    private String mattername;
//    /**
//     * 产品单价
//     */
////    private Double productPrice;
////    /**
////     * 产品数量
////     */
////    private Double productNum;
    /**
     * 订单id
     */
    private int orderInfoId;
}
