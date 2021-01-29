package com.zx.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.pro.entity.WorkOrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface WorkOrderInfoMapper extends BaseMapper<WorkOrderInfo> {

    @Update("update work_order_info set state = #{state} where work_order = #{workOrder}")
    int updateState(String workOrder, String state);
}
