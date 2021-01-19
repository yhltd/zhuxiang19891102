package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterProduct;
import com.zx.pro.entity.ProjectInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
     * @return 所有项目集合
     */
    List<ProjectInfo> getList();

    /**
     * 条件查询
     * @param projectName 项目名
     * @param startDate 查询开始日期
     * @param endDate 查询结束日期
     * @return
     */
    List<ProjectInfo> getList(String projectName, String startDate,String endDate);

    /**
     * 添加新的项目
     * @param projectInfo 项目
     * @param list 所需物料的集合
     * @return 是否添加成功
     */
    ProjectInfo add(ProjectInfo projectInfo, List<MatterProduct> list);

    /**
     * 添加新的项目
     * @param projectInfo 项目
     * @return 是否添加成功
     */
    ProjectInfo add(ProjectInfo projectInfo);

    /**
     * 修改项目
     * @param projectInfo 新的项目信息
     * @return 是否成功
     */
    boolean update(ProjectInfo projectInfo);

    /**
     * 删除项目
     * @param id id
     * @return 是否成功
     */
    boolean delete(int id);

    /**
     * 批量删除项目
     * @param idList id数组
     * @return 是否成功
     */
    boolean delete(List<Integer> idList);
}
