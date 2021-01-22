package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dai
 */
@Data
@TableName("product_matter")
public class ProductMatter{

    /**
     * 产品表id
     */
    private int productInfoId;
    /**
     * 物料项目表id
     */
    private int matterProjectId;
    /**
     * 所用数量
     */
    private Double num;
    /**
     * 单价
     */
    private Double price;
}
