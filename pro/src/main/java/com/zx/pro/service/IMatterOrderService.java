package com.zx.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zx.pro.entity.MatterInfoItem;
import com.zx.pro.entity.MatterOrder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物料订单表接口
 * @author wanghui
 */
@Service
public interface IMatterOrderService extends IService<MatterOrder> {
    /**
     * 查询物料订单表
     * @return 物料订单表信息的集合
     */
    List<MatterOrder> getMatterOrder();

    /**
     * 修改物料订单表
     * @param matterOrder 修改过的物料订单表对象
     * @return 是否修改成功
     */
    boolean updateMatterOrder(MatterOrder matterOrder);

    /**
     * 添加物料订单表
     * @param matterOrder 添加物料订单表对象
     * @return 是否添加成功
     */
    boolean addMatterOrder(MatterOrder matterOrder);

    /**
     * 删除
     * @param id 根据id删除
     * @return 是否删除成功
     */
    boolean deleteMatterOrder(int id);

    /**
     * 查询物料表
     * @param orderId 根据订单id查询
     * @return 返回物料表信息的集合
     */
    List<MatterInfoItem> getMatterByOrderId(int orderId);
}
