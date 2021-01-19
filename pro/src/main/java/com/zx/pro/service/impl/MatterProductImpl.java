package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.*;
import com.zx.pro.mapper.MatterProductMapper;
import com.zx.pro.service.IMatterProductChangeService;
import com.zx.pro.service.IMatterProductService;
import com.zx.pro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author dai
 */
@Service
public class MatterProductImpl extends ServiceImpl<MatterProductMapper, MatterProduct> implements IMatterProductService {

    @Autowired
    private IMatterProductChangeService iMatterProductChangeService;

    @Override
    public List<MatterProduct> getList() {
        return this.list();
    }

    @Override
    public List<MatterProduct> getList(int projectInfoId) {
        //条件构造器
        QueryWrapper<MatterProduct> queryWrapper = new QueryWrapper<>();
        //project_info_id = projectInfoId
        queryWrapper.eq("project_info_id", projectInfoId);
        return this.list(queryWrapper);
    }

    @Override
    public boolean update(MatterProduct matterProduct) {
        return this.updateById(matterProduct);
    }

    @Override
    public boolean update(List<MatterProduct> matterProduct) {
        //修改批次50
        return this.updateBatchById(matterProduct, 50);
    }

    @Override
    public MatterProduct add(MatterProduct matterProduct) {
        return this.save(matterProduct) ? matterProduct : null;
    }

    @Override
    public boolean add(List<MatterProduct> list) {
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
        QueryWrapper<MatterProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("project_info_id", idList);
        return this.remove(queryWrapper);
    }

    @Override
    public boolean change(List<MatterProduct> newMatterProductList, Integer projectInfoId, UserInfo userInfo) {
        //创建项目物料变化表的集合
        List<MatterProductChange> matterProductChanges = new ArrayList<>();
        //获取该表修改前的集合
        List<MatterProduct> matterProductList = this.getList(projectInfoId);
        if (StringUtils.isNotNull(matterProductList)) {
            newMatterProductList.forEach(newMatterProduct -> {
                matterProductList.forEach(matterProduct -> {
                    //修改后的物料数量
                    Double matterNum = newMatterProduct.getMatterNum();
                    //物料id
                    Integer matterInfoId = newMatterProduct.getMatterInfoId();
                    //判断是否修改
                    if(matterInfoId == matterProduct.getMatterInfoId() &&
                            matterNum != matterProduct.getMatterNum()){
                        //项目物料变化表
                        MatterProductChange matterProductChange = new MatterProductChange();
                        //项目物料表id
                        matterProductChange.setMatterProductId(matterProduct.getId());
                        //修改前数量
                        matterProductChange.setOldNum(matterProduct.getMatterNum());
                        //修改后数量
                        matterProductChange.setNewNum(matterNum);
                        //修改人
                        matterProductChange.setUpdateMan(userInfo.getName());
                        //修改时间
                        matterProductChange.setUpdateTime(LocalDateTime.now());
                        //插入项目物料变化表
                        matterProductChanges.add(matterProductChange);

                        //插入修改后数量
                        matterProduct.setMatterNum(matterNum);
                    }
                });
            });

            //修改项目物料表
            if(this.update(matterProductList)){
                //添加修改记录到项目物料变化表
                return iMatterProductChangeService.add(matterProductChanges);
            }
        }
        return false;
    }

}
