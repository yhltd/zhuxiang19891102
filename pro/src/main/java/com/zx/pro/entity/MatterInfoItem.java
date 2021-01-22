package com.zx.pro.entity;

import lombok.Data;

/**
 * @author dai
 */
@Data
public class MatterInfoItem extends MatterInfo{

    /**
     * 项目物料表id
     */
    private int matterProjectId;
    /**
     * 总数量
     */
    private Double matterNum;
    /**
     * 已用数量
     */
    private Double num;
    /**
     * 单价
     */
    private Double price;
}
