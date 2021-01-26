package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.OrderInfo;
import com.zx.pro.entity.OrderInfoItem;
import com.zx.pro.entity.OrderState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dai
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     * 查询所有订单信息
     *
     * @return
     */
    @Select("select pi.project_name,oi.* from order_info as oi " +
            "left join project_info as pi " +
            "on oi.project_info_id = pi.id")
    List<OrderInfoItem> postList();

    /**
     * 条件查询订单信息
     *
     * @param projectName 项目名称
     * @param orderId     订单号
     * @param startDate   起始创建时间
     * @param endDate     截止创建时间
     * @return
     */
    @Select("select pi.project_name,oi.* from order_info as oi " +
            "left join project_info as pi " +
            "on oi.project_info_id = pi.id " +
            "where pi.project_name like CONCAT('%',#{projectName},'%') " +
            "and oi.order_id like CONCAT('%',#{orderId},'%')" +
            "and create_time between #{startDate} and #{endDate})")
    List<OrderInfoItem> getList(String projectName,
                                String orderId,
                                LocalDateTime startDate,
                                LocalDateTime endDate);

    /**
     * 查询订单状态
     *
     * @return
     */
    @Select("select pri.project_name,oi.*,sum(pi.product_num) as order_num,min(wod.work_date) as work_date,sum(wod.work_num) as work_num " +
            "from order_info as oi " +
            "left join project_info as pri " +
            "on oi.project_info_id = pri.id " +
            "left join product_info as pi " +
            "on oi.order_id = pi.order_info_id " +
            "left join work_order_detail as wod " +
            "on wod.product_info_id = pi.id" +
            "where oi.order_id like CONCAT('%','','%')")
    List<OrderState> getOrderStateList();


    /**
     * 条件查询订单状态
     * @param orderId 订单号
     * @return
     */
    @Select("select pri.project_name,oi.*,sum(pi.product_num) as order_num,min(wod.work_date) as work_date,sum(wod.work_num) as work_num " +
            "from order_info as oi " +
            "left join project_info as pri " +
            "on oi.project_info_id = pri.id " +
            "left join product_info as pi " +
            "on oi.order_id = pi.order_info_id " +
            "left join work_order_detail as wod " +
            "on wod.product_info_id = pi.id " +
            "where oi.order_id like CONCAT('%',#{orderId},'%')")
    List<OrderState> getOrderStateList(String orderId);
}