package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.WorkOrderDetail;
import com.zx.pro.entity.WorkOrderDetailItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Mapper
public interface WorkOrderDetailMapper extends BaseMapper<WorkOrderDetail> {
    /**
     * 查询
     *
     * @param workOrder 根据派工单单号查询
     * @return 派工单明细集合
     */
    @Select("SELECT p.product_name,wi.work_order,o.order_id,wd.* from work_order_info wi right join work_order_detail wd on wd.work_order_info_id=wi.id LEFT JOIN product_info p on wd.product_info_id = p.id LEFT JOIN order_info o on p.id=o.project_info_id where wi.work_order= #{workOrder} ")
    List<WorkOrderDetailItem> getListByWorkOrder(String workOrder);

    /**
     * 查询全部
     *
     * @return 派工单明细集合
     */
    @Select("SELECT p.product_name,wi.work_order,o.order_id,wd.* from work_order_info wi right join work_order_detail wd on wd.work_order_info_id=wi.id LEFT JOIN product_info p on wd.product_info_id = p.id LEFT JOIN order_info o on p.id=o.project_info_id order by wd.work_date desc")
    List<WorkOrderDetailItem> getList();

    /**
     * 查询
     *
     * @return 车间生产信息集合
     */
    @Select("select wod.work_shop,pi.product_name,sum(wod.work_num) as work_num from work_order_detail as wod left join product_info as pi on wod.product_info_id = pi.id group by wod.work_shop,pi.id")
    List<WorkOrderDetailItem> getProductionListByWorkShop();


    /**
     * 查询
     *
     * @return 产线生产信息集合
     */
    @Select("select wod.work_line,pi.product_name,sum(wod.work_num) as work_num from work_order_detail as wod left join product_info as pi on wod.product_info_id = pi.id group by wod.work_line,pi.id")
    List<WorkOrderDetailItem> getProductionListByWorkLine();


    /**
     * 根据时间范围查询
     *
     * @return 车间生产信息集合
     */
    @Select("select wod.work_shop,pi.product_name,sum(wod.work_num) as work_num from work_order_detail as wod left join product_info as pi on wod.product_info_id = pi.id where wod.work_date between #{startDate} and #{endDate} group by wod.work_shop,pi.id")
    List<WorkOrderDetailItem> getWorkShopByWorkDate(LocalDateTime startDate, LocalDateTime endDate);


    /**
     * 根据时间范围查询
     *
     * @return 产线生产信息集合
     */
    @Select("select wod.work_line,pi.product_name,sum(wod.work_num) as work_num from work_order_detail as wod left join product_info as pi on wod.product_info_id = pi.id where wod.work_date between #{startDate} and #{endDate} group by wod.work_line,pi.id")
    List<WorkOrderDetailItem> getWorkLineByWorkDate(LocalDateTime startDate, LocalDateTime endDate);


}
