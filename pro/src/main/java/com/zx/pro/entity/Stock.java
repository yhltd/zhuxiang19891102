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
     * 物料id
     */
    private int matterId;

    /**
     * 库存数量
     */
    private Double stockNum;
}
