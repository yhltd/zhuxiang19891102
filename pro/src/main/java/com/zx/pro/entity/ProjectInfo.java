package com.zx.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("project_info")
public class ProjectInfo {

    /**
     * 主键
     */
    @TableId(value = "id" , type = IdType.AUTO)
    private int id;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 项目地址
     */
    private String projectAddress;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
