package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.OutStockDetail;
import com.zx.pro.entity.ProductInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dai
 */
@Mapper
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    /**
     * 根据订单号查询所有可出库产品
     * @param orderId 订单号
     * @return
     */
    @Select("select pi.*,pi.id as product_info_id,s.stock_num as out_num " +
            "from product_info as pi " +
            "left join stock as s " +
            "on pi.id = s.product_info_id " +
            "where pi.order_info_id = #{orderId}")
    List<OutStockDetail> getList(String orderId);
}
