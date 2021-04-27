package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.*;
import com.zx.pro.mapper.MatterProjectMapper;
import com.zx.pro.service.IMatterProjectChangeService;
import com.zx.pro.service.IMatterProjectService;
import com.zx.pro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author dai
 */
@Service
public class MatterProjectImpl extends ServiceImpl<MatterProjectMapper, MatterProject> implements IMatterProjectService {

    @Autowired
    private IMatterProjectChangeService iMatterProjectChangeService;

    @Override
    public List<MatterProject> getList() {
        return this.list();
    }

    @Override
    public List<MatterProject> getList(int projectInfoId) {
        //条件构造器
        QueryWrapper<MatterProject> queryWrapper = new QueryWrapper<>();
        //project_info_id = projectInfoId
        queryWrapper.eq("project_info_id", projectInfoId);
        return this.list(queryWrapper);
    }

    @Override
    public List<MatterProject> getList(List<Integer> projectInfoIdList) {
        QueryWrapper<MatterProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("project_info_id",projectInfoIdList);
        return this.list(queryWrapper);
    }

    @Override
    public boolean update(MatterProject matterProduct) {
        return this.updateById(matterProduct);
    }

    @Override
    public boolean update(List<MatterProject> matterProduct) {
        //修改批次50
        return this.updateBatchById(matterProduct, 50);
    }

    @Override
    public MatterProject add(MatterProject matterProduct) {
        return this.save(matterProduct) ? matterProduct : null;
    }

    @Override
    public boolean add(List<MatterProject> list) {
        //批量插入，插入批次10
        return this.saveBatch(list, 50);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        return this.removeByIds(idList);
    }

    @Override
    public boolean deleteByProjectId(List<Integer> idList) {
        QueryWrapper<MatterProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("project_info_id", idList);
        return this.remove(queryWrapper);
    }

    @Override
    public boolean change(List<MatterProject> newMatterProductList, Integer projectInfoId, UserInfo userInfo) {
        //创建项目物料变化表的集合
        List<MatterProjectChange> matterProjectChanges = new ArrayList<>();
        //获取该表修改前的集合
        List<MatterProject> matterProductList = this.getList(projectInfoId);
        if (StringUtils.isNotNull(matterProductList)) {
            newMatterProductList.forEach(newMatterProduct -> {
                matterProductList.forEach(matterProduct -> {
                    //修改后的物料数量
                    Double matterNum = newMatterProduct.getMatterNum();
                    //物料id
                    Integer matterInfoId = newMatterProduct.getMatterInfoId();
                    //判断是否修改
                    if(matterInfoId == matterProduct.getMatterInfoId() &&
                            StringUtils.isNotEqual(matterNum,matterProduct.getMatterNum())){
                        //项目物料变化表
                        MatterProjectChange matterProjectChange = new MatterProjectChange();
                        //项目物料表id
                        matterProjectChange.setMatterInfoId(matterProduct.getId());
                        //修改前数量
                        matterProjectChange.setOldNum(matterProduct.getMatterNum());
                        //修改后数量
                        matterProjectChange.setNewNum(matterNum);
                        //修改人
                        matterProjectChange.setUpdateMan(userInfo.getName());
                        //修改时间
                        matterProjectChange.setUpdateTime(LocalDateTime.now());
                        //插入项目物料变化表
                        matterProjectChanges.add(matterProjectChange);

                        //插入修改后数量
                        matterProduct.setMatterNum(matterNum);
                    }
                });
            });

            //修改项目物料表
            if(this.update(matterProductList)){
                //添加修改记录到项目物料变化表
                return iMatterProjectChangeService.add(matterProjectChanges);
            }
        }
        return false;
    }

}
