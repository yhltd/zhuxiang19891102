package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author dai
 */
@Data
@TableName("set_stock_detail")
public class SetStockDetail {

    /**
     * 主键id
     */
    @TableId(value = "set_stock_id", type = IdType.AUTO)
    private int setStockId;
    /**
     * 产品信息id
     */
    private int productInfoId;
    /**
     * 入库单号
     */
    private String setOrder;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 入库数量
     */
    private Double setNum;
    /**
     * 入库人
     */
    private String man;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品单价
     */
    private Double productPrice;
}
