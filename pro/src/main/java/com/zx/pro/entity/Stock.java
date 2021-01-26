package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dai
 */
@Data
@TableName("stock")
public class Stock {

    /**
     * 主键id
     */
    @TableId(value = "stock_id", type = IdType.AUTO)
    private int stockId;
    /**
     * 产品信息id
     */
    private int productInfoId;
    /**
     * 库存数量
     */
    private Double stockNum;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品单价
     */
    private String productPrice;
}
