package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.MatterOrder;
import com.zx.pro.entity.Order_matter;
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
    @Select("select pi.*,pi.id as product_info_id,s.stock_num as out_num " +
            "from product_info as pi " +
            "left join stock as s " +
            "on pi.id = s.product_info_id " +
            "where pi.order_info_id = #{orderId}")
    List<OutStockDetail> getList(String orderId);


    /**
     * 查询所有的订单以及对应物料
     * @return
     */
    @Select("SELECT mo.id as id , mo.matter_id as matterid , mo.order_id as orderid ,mo.num as matternum ,mi.type as type ,mi.size as size ,mi.yield_strength as yie,mi.color as color, mi.code as mattername FROM `matter_order` as mo ,matter_info as mi WHERE mo.matter_id=mi.id")
    List<Order_matter> getmoList();


    /**
     * 模糊查询
     * @param
     */
    @Select("select mo.id as id , mo.matter_id as matterid , mo.order_id as orderid ,mo.num as matternum ,mi.type as type ,mi.size as size ,mi.yield_strength as yie,mi.color as color, mi.code as mattername  FROM `matter_order` as mo ,matter_info as mi WHERE mo.matter_id=mi.id and mo.order_id like '%${orderId}%' and mi.code like '%${productName}%'")
    List<Order_matter> getmoonList(String orderId,String productName);


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
