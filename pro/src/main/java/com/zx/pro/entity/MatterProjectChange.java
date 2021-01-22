package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 项目所需物料变化
 *
 * @author dai
 */
@Data
@TableName("matter_project_change")
public class MatterProjectChange {

    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    /**
     * 项目物料表
     */
    private int matterProjectId;
    /**
     * 修改前数量
     */
    private Double oldNum;
    /**
     * 修改后数量
     */
    private Double newNum;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 修改人
     */
    private String updateMan;
}
