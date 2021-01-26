package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dai
 */
@Data
@TableName("product_info")
public class ProductInfo {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    /**
     * 订单表id
     */
    private int orderInfoId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品数量
     */
    private Double productNum;
}
