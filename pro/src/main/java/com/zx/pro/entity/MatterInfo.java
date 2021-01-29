package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("matter_info")
public class MatterInfo {
    /**
     * 主键
     */
    @TableId(value = "id" , type = IdType.AUTO)
    private int id;
    /**
     * 物料代码
     */
    private String code;

    /**
     * 类别
     */
    private String type;
    /**
     * 长度（英尺）
     */
    private Double size;
    /**
     * 屈服强度
     */
    private Double yieldStrength;
    /**
     * 图层厚度
     */
    private String chartThickness;
    /**
     * 颜色
     */
    private String color;
    /**
     * 配件比例
     */
    private String fittingsProportion;
    /**
     * 配件数量
     */
    private Double fittingsNum;
    /**
     * 供应商
     */
    private String supplier;
}

