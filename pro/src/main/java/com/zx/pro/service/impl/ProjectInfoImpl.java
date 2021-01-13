package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterProduct;
import com.zx.pro.entity.ProjectInfo;
import com.zx.pro.mapper.ProjectInfoMapper;
import com.zx.pro.service.IMatterProductService;
import com.zx.pro.service.IProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public boolean add(ProjectInfo projectInfo, List<MatterProduct> list) {
        //新增项目
        if(this.save(projectInfo)){
            //循环填入项目id
            for(MatterProduct matterProduct : list){
                matterProduct.setProjectInfoId(projectInfo.getId());
            }
            //批量插入
            return iMatterProductService.add(list);
        }else{
            return false;
        }
    }

    @Override
    public boolean add(ProjectInfo projectInfo) {
        return this.save(projectInfo);
    }
}
