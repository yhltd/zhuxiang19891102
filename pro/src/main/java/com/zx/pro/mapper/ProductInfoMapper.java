package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.OutStockDetail;
import com.zx.pro.entity.ProductInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Select("select mo.*,mo.id as product_info_id,s.stock_num as out_num from matter_order as mo left join stock as s on mo.matter_id = s.matter_id where mo.order_id = #{orderId}")
    List<OutStockDetail> getList(String orderId);


    @Select("SELECT id FROM matter_info WHERE code=#{mattername}")
    int getid(String mattername);

    @Update("UPDATE matter_order set num=#{num} , matter_id=#{metterid}  where id=#{moid}")
    int updatematterorder(int metterid,int moid,int num);

    @Delete("delete from matter_order  where id=#{id}")
    int deletemo(int id);

    @Select("select order_id from matter_order  where id=#{id}")
    String selectgteorderid(int id);

    @Delete("delete from order_info  where id=#{id}")
    int deleteorderid(int id);

}
