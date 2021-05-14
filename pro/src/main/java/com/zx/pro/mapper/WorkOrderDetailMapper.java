package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.WorkOrderDetail;
import com.zx.pro.entity.WorkOrderDetailItem;
import org.apache.ibatis.annotations.Insert;
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
    @Select("SELECT m.code as mattername,wi.work_order,o.order_id,wd.* from work_order_info wi right join work_order_detail wd on wd.work_order_info_id=wi.id LEFT JOIN matter_info m on wd.matter_id= m.id LEFT JOIN order_info o on m.id=o.matterid where wi.work_order= #{workOrder} ")
    List<WorkOrderDetailItem> getListByWorkOrder(String workOrder);

    /**
     * 查询全部
     *
     * @return 派工单明细集合
     */
    @Select("SELECT m.code as mattername,wi.work_order,o.order_id,wd.* from work_order_info wi right join work_order_detail wd on wd.work_order_info_id=wi.id LEFT JOIN matter_info m on wd.matter_id = m.id LEFT JOIN order_info o on m.id=o.matterid order by wd.work_date desc")
    List<WorkOrderDetailItem> getList();

    /**
     * 查询
     *
     * @return 车间生产信息集合
     */
    @Select("select wod.work_shop,m.code as mattername,sum(wod.work_num) as work_num from work_order_detail as wod left join matter_info as m on wod.matter_id = m.id group by wod.work_shop,m.id")
    List<WorkOrderDetailItem> getProductionListByWorkShop();


    /**
     * 查询
     *
     * @return 产线生产信息集合
     */
    @Select("select wod.work_line,m.code as mattername,sum(wod.work_num) as work_num from work_order_detail as wod left join matter_info as m on wod.matter_id = m.id group by wod.work_line,m.id")
    List<WorkOrderDetailItem> getProductionListByWorkLine();


    /**
     * 根据时间范围查询
     *
     * @return 车间生产信息集合
     */
    @Select("select wod.work_shop,m.code as mattername,sum(wod.work_num) as work_num from work_order_detail as wod left join matter_info as m on wod.matter_id = m.id where wod.work_date between #{startDate} and #{endDate} group by wod.work_shop,m.id")
    List<WorkOrderDetailItem> getWorkShopByWorkDate(LocalDateTime startDate, LocalDateTime endDate);


    /**
     * 根据时间范围查询
     *
     * @return 产线生产信息集合
     */
    @Select("select wod.work_line,m.code as mattername,sum(wod.work_num) as work_num from work_order_detail as wod left join matter_info as m on wod.matter_id = m.id where wod.work_date  between #{startDate} and #{endDate} group by wod.work_line,m.id")
    List<WorkOrderDetailItem> getWorkLineByWorkDate(LocalDateTime startDate, LocalDateTime endDate);


}
