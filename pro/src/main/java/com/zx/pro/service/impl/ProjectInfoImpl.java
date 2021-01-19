package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterProduct;
import com.zx.pro.entity.ProjectInfo;
import com.zx.pro.mapper.ProjectInfoMapper;
import com.zx.pro.service.IMatterProductService;
import com.zx.pro.service.IProjectInfoService;
import com.zx.pro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目信息接口实现类
 *
 * @author dai
 */
@Service
public class ProjectInfoImpl extends ServiceImpl<ProjectInfoMapper, ProjectInfo> implements IProjectInfoService {

    @Autowired
    private IMatterProductService iMatterProductService;

    @Override
    public List<ProjectInfo> getList() {
        return this.list();
    }

    @Override
    public List<ProjectInfo> getList(String projectName, String startDateStr, String endDateStr) {
        QueryWrapper<ProjectInfo> queryWrapper = new QueryWrapper<>();
        //模糊查询项目名
        if(StringUtils.isNotEmpty(projectName)){
            queryWrapper.like("project_name", projectName);
        }
        //开始时间
        LocalDateTime startDate = StringUtils.isNotEmpty(startDateStr) ?
                LocalDateTime.parse(startDateStr) :
                StringUtils.MIN_DATETIME;
        //结束时间
        LocalDateTime endDate = StringUtils.isNotEmpty(endDateStr) ?
                LocalDateTime.parse(endDateStr) :
                StringUtils.MAX_DATETIME;

        //时间区间查询
        queryWrapper.between("create_time", startDate, endDate);
        return this.list(queryWrapper);
    }

    @Override
    public ProjectInfo add(ProjectInfo projectInfo, List<MatterProduct> list) {
        projectInfo.setCreateTime(LocalDateTime.now());
        //新增项目
        if (this.save(projectInfo)) {
            //循环填入项目id
            for (MatterProduct matterProduct : list) {
                matterProduct.setProjectInfoId(projectInfo.getId());
            }
            //批量插入
            return iMatterProductService.add(list) ? projectInfo : null;
        } else {
            return null;
        }
    }

    @Override
    public ProjectInfo add(ProjectInfo projectInfo) {
        return this.save(projectInfo) ? projectInfo : null;
    }

    @Override
    public boolean update(ProjectInfo projectInfo) {
        return this.updateById(projectInfo);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        if(this.removeByIds(idList)){
            return iMatterProductService.deleteByProjectId(idList);
        }
        return false;
    }
}
