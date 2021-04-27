package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterProject;
import com.zx.pro.entity.MatterProjectChange;
import com.zx.pro.entity.MatterProjectChangeItem;
import com.zx.pro.entity.ProductInfo;
import com.zx.pro.mapper.MatterProjectChangeMapper;
import com.zx.pro.service.IMatterProjectChangeService;
import com.zx.pro.service.IMatterProjectService;
import com.zx.pro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dai
 */
@Service
public class MatterProjectChangeImpl extends ServiceImpl<MatterProjectChangeMapper, MatterProjectChange> implements IMatterProjectChangeService {

    @Autowired
    private IMatterProjectService iMatterProjectService;

    @Autowired
    private MatterProjectChangeMapper matterProjectChangeMapper;

    @Override
    public List<MatterProjectChangeItem> getList() {
        return matterProjectChangeMapper.getList();
    }

    @Override
    public List<MatterProjectChangeItem> getList(String projectName, String code, String updateMan) {
        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("pi.project_name", projectName);
        queryWrapper.like("mi.code",code);
        queryWrapper.like("mpc.update_man",updateMan);
        queryWrapper.orderByDesc("mpc.update_time");
        return matterProjectChangeMapper.selectList(projectName,code,updateMan);
    }

    @Override
    public List<MatterProjectChange> getList(int projectInfoId) {
        //获取project_info_id==projectInfoId的项目物料
        List<MatterProject> list = iMatterProjectService.getList(projectInfoId);
        //判断不为空
        if (StringUtils.isNotNull(list)) {
            List<Integer> idList = new ArrayList<>();
            //循环获取Id
            list.forEach(matterProduct -> {
                idList.add(matterProduct.getId());
            });
            //条件构造器
            QueryWrapper<MatterProjectChange> queryWrapper = new QueryWrapper<>();
            //条件
            queryWrapper.in("matter_product_id", idList);
            return this.list(queryWrapper);
        }
        return null;
    }

    @Override
    public MatterProjectChange add(MatterProjectChange matterProjectChange) {
        return this.save(matterProjectChange) ? matterProjectChange : null;
    }

    @Override
    public boolean add(List<MatterProjectChange> list) {
        //插入批次10
        return this.saveBatch(list, 50);
    }

    @Override
    public boolean update(MatterProjectChange matterProjectChange) {
        return this.updateById(matterProjectChange);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        return removeByIds(idList);
    }

    @Override
    public boolean deleteByMatterProjectId(List<Integer> matterProductIdList) {
        QueryWrapper<MatterProjectChange> matterProductChangeQueryWrapper = new QueryWrapper<>();
        matterProductChangeQueryWrapper.in("matter_info_id", matterProductIdList);
        return remove(matterProductChangeQueryWrapper);
    }
}
