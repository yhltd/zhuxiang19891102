package com.zx.pro.entity;

import lombok.Data;

/**
 * @author dai
 */
@Data
public class MatterProjectChangeItem extends MatterProjectChange {

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 物料编码
     */
    private String code;
}
