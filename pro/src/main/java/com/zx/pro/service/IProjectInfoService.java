package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterProduct;
import com.zx.pro.entity.ProjectInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目信息接口
 *
 * @author dai
 */
@Service
public interface IProjectInfoService extends IService<ProjectInfo> {

    /**
     * 获取项目集合
     * @return
     */
    List<ProjectInfo> getList();

    /**
     * 添加新的项目
     * @param projectInfo 项目
     * @param list 所需物料的集合
     * @return 是否添加成功
     */
    boolean add(ProjectInfo projectInfo, List<MatterProduct> list);

    /**
     * 添加新的项目
     * @param projectInfo 项目
     * @return 是否添加成功
     */
    boolean add(ProjectInfo projectInfo);
}
