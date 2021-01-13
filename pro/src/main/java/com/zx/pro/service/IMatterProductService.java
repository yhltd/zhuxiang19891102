package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterProduct;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物料订单表接口
 *
 * @author dai
 */
@Service
public interface IMatterProductService extends IService<MatterProduct> {
    /**
     * 查询物料订单表
     * @return 物料订单表信息的集合
     */
    List<MatterProduct> getList();

    /**
     * 修改物料订单表
     * @param matterProduct 修改过的物料订单表对象
     * @return 是否修改成功
     */
    boolean update(MatterProduct matterProduct);

    /**
     * 添加物料订单
     * @param matterProduct 添加物料订单表对象
     * @return 是否添加成功
     */
    boolean add(MatterProduct matterProduct);

    /**
     * 批量添加物料订单
     * @param list
     * @return
     */
    boolean add(List<MatterProduct> list);

    /**
     * 删除
     * @param id 根据id删除
     * @return 是否删除成功
     */
    boolean delete(int id);

}
