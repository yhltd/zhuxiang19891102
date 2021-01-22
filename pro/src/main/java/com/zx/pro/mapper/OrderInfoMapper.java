package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.OrderInfo;
import com.zx.pro.entity.OrderInfoItem;
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
     * @return
     */
    @Select("select pi.project_name,oi.* from order_info as oi " +
            "left join project_info as pi " +
            "on oi.project_info_id = pi.id")
    List<OrderInfoItem> getList();

    /**
     * 条件查询订单信息
     * @param projectName 项目名称
     * @param orderId 订单号
     * @param startDate 起始创建时间
     * @param endDate 截止创建时间
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
}