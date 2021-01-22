package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@TableName("matter_project")
public class MatterProject{
    /**
     * 主键
     */
    @TableId(value = "id" , type = IdType.AUTO)
    private int id;
    /**
     * 项目id
     */
    private int projectInfoId;
    /**
     * 物料id
     */
    private int matterInfoId;
    /**
     * 数量
     */
    private Double matterNum;
}
