package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.MatterProduct;
import com.zx.pro.entity.MatterProductChange;
import com.zx.pro.mapper.MatterProductChangeMapper;
import com.zx.pro.service.IMatterProductChangeService;
import com.zx.pro.service.IMatterProductService;
import com.zx.pro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dai
 */
@Service
public class MatterProductChangeImpl extends ServiceImpl<MatterProductChangeMapper, MatterProductChange> implements IMatterProductChangeService {

    @Autowired
    private IMatterProductService iMatterProductService;

    @Override
    public List<MatterProductChange> getList() {
        return this.list();
    }

    @Override
    public List<MatterProductChange> getList(int projectInfoId) {
        //获取project_info_id==projectInfoId的项目物料
        List<MatterProduct> list = iMatterProductService.getList(projectInfoId);
        //判断不为空
        if(StringUtils.isNotNull(list)){
            List<Integer> idList = new ArrayList<>();
            //循环获取Id
            list.forEach(matterProduct -> {
                idList.add(matterProduct.getId());
            });
            //条件构造器
            QueryWrapper<MatterProductChange> queryWrapper = new QueryWrapper<>();
            //条件
            queryWrapper.in("matter_product_id",idList);
            return this.list(queryWrapper);
        }
        return null;
    }

    @Override
    public MatterProductChange add(MatterProductChange matterProductChange) {
        return this.save(matterProductChange) ? matterProductChange : null;
    }

    @Override
    public boolean add(List<MatterProductChange> list) {
        //插入批次10
        return this.saveBatch(list,10);
    }

    @Override
    public boolean update(MatterProductChange matterProductChange) {
        return this.updateById(matterProductChange);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public boolean delete(List<Integer> idList) {
        return removeByIds(idList);
    }
}
