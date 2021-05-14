package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterProject;
import com.zx.pro.entity.ProjectInfo;
import com.zx.pro.mapper.MatterInfoMapper;
import com.zx.pro.mapper.ProjectInfoMapper;
import com.zx.pro.service.IMatterProjectChangeService;
import com.zx.pro.service.IMatterProjectService;
import com.zx.pro.service.IOrderInfoService;
import com.zx.pro.service.IProjectInfoService;
import com.zx.pro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目信息接口实现类
 *
 * @author dai
 */
@Service
public class ProjectInfoImpl extends ServiceImpl<ProjectInfoMapper, ProjectInfo> implements IProjectInfoService {

    @Autowired
    private IOrderInfoService iOrderInfoService;

    @Autowired
    private IMatterProjectService iMatterProjectService;

    @Autowired
    private IMatterProjectChangeService iMatterProjectChangeService;

    @Autowired
    private MatterInfoMapper matterInfoMapper;

    @Override
    public List<ProjectInfo> getList() {
        return matterInfoMapper.getListByProject();
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
    public ProjectInfo add(ProjectInfo projectInfo, List<MatterProject> list) {
        projectInfo.setCreateTime(LocalDateTime.now());
        //新增项目
        if (this.save(projectInfo)) {
            //循环填入项目id
            for (MatterProject matterProject : list) {
                matterProject.setProjectInfoId(projectInfo.getId());
            }
            //批量插入
            return iMatterProjectService.add(list) ? projectInfo : null;
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
        //获取选择的项目下的项目物料信息
        List<MatterProject> matterProjectList = iMatterProjectService.getList(idList);
        //循环获取id
        if(StringUtils.isNotNull(matterProjectList)) {
            List<Integer> matterProductIdList = new ArrayList<>();
            for(MatterProject matterProject : matterProjectList){
                matterProductIdList.add(matterProject.getId());
            }

            //删除该项目下的所有订单
            iOrderInfoService.deleteByProjectId(idList);
            //删除项目物料需求变化表
            iMatterProjectChangeService.deleteByMatterProjectId(matterProductIdList);
            //删除项目关联的项目物料表
            iMatterProjectService.deleteByProjectId(idList);
            //删除项目信息
            return this.removeByIds(idList);
        }
        return false;
    }
}
