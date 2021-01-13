package com.zx.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.pro.entity.MatterProduct;
import com.zx.pro.mapper.MatterProductMapper;
import com.zx.pro.service.IMatterProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dai
 */
@Service
public class MatterProductImpl extends ServiceImpl<MatterProductMapper, MatterProduct> implements IMatterProductService {

    @Override
    public List<MatterProduct> getList() {
        return this.list();
    }

    @Override
    public boolean update(MatterProduct matterProduct) {
        UpdateWrapper<MatterProduct> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id", matterProduct.getId());
        return this.update(matterProduct,updateWrapper);
    }

    @Override
    public boolean add(MatterProduct matterProduct) {
        return this.save(matterProduct);
    }

    @Override
    public boolean add(List<MatterProduct> list) {
        //批量插入，插入批次10
        return this.saveBatch(list,10);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }
}
