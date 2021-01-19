package com.zx.pro;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.zx.pro.entity.*;
import com.zx.pro.service.IMatterInfoService;
import com.zx.pro.service.IProjectInfoService;
import com.zx.pro.service.IWorkOrderDetailService;
import com.zx.pro.service.IWorkOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProApplication.class)
public class ProApplicationTests {
    //项目Service
    @Autowired
    IProjectInfoService iProjectInfoService;

    //物料Service
    @Autowired
    IMatterInfoService iMatterInfoService;

    //派工单Service
    @Autowired
    IWorkOrderInfoService iWorkOrderInfoService;

    //派工单明细Service
    @Autowired
    IWorkOrderDetailService iWorkOrderDetailService;

    /**
     * 测试插入项目信息
     */
    @Test
    public void ProjectInfoTest() {
        //项目集合
        List<ProjectInfo> projectInfoList = iProjectInfoService.getList();
        log.info("projectInfoList：{}", projectInfoList);

        //新项目
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectName("测试项目1");
        projectInfo.setCustomerName("测试客户1");
        projectInfo.setProjectAddress("测试项目地址1");
        projectInfo.setCreateTime(LocalDateTime.now());

        //物料集合
        List<MatterInfo> matterInfoList = iMatterInfoService.getList();
        log.info("matterInfoList：{}", matterInfoList);

        //选择物料
        List<MatterProduct> matterProductList = new ArrayList<>();
        //选择一个物料
        MatterProduct matterProduct1 = new MatterProduct();
        //设置物料id
        matterProduct1.setProductInfoId(matterInfoList.get(0).getId());
        //设置所需数量
        matterProduct1.setMatterNum(20);
        //添加到集合中
        matterProductList.add(matterProduct1);
        log.info("matterProductList：{}", matterProductList);

        //插入项目信息
        boolean result = iProjectInfoService.add(projectInfo, matterProductList);
        log.info("是否插入成功：{}", result);
    }

    @Test
    public void WorkOrderDetail() {
        //WorkOrderInfo workOrderInfo=new WorkOrderInfo();
        WorkOrderDetail workOrderDetail = new WorkOrderDetail();
        workOrderDetail.setId(3);
        workOrderDetail.setWorkOrderInfoId(3);
        workOrderDetail.setProductInfoId(3);
        workOrderDetail.setWorkDate(new Date());
        workOrderDetail.setWorkShop("车间3");
        workOrderDetail.setWorkLine("产线3");
        workOrderDetail.setWorkNum(300);

        WorkOrderDetail workOrderDetail2 = new WorkOrderDetail();
        workOrderDetail2.setId(4);
        workOrderDetail2.setWorkOrderInfoId(4);
        workOrderDetail2.setProductInfoId(4);
        workOrderDetail2.setWorkDate(new Date());
        workOrderDetail2.setWorkShop("车间4");
        workOrderDetail2.setWorkLine("产线4");
        workOrderDetail2.setWorkNum(400);

        List<WorkOrderDetail> addList = new ArrayList<>();
        addList.add(workOrderDetail);
        addList.add(workOrderDetail2);


        //添加
        //iWorkOrderDetailService.add(workOrderDetail);

        //批量添加
        boolean add = iWorkOrderDetailService.add(addList);
        log.info("add：{}", add);

        //修改
        //boolean update=iWorkOrderDetailService.update(workOrderDetail);
        //log.info("update：{}",update);

        //删除
        //boolean delete=iWorkOrderDetailService.delete(1);
        //log.info("delete：{}",delete);

        //查询
//        List<WorkOrderDetail> workOrderDetailList = iWorkOrderDetailService.getList(1);
//        log.info("workOrderDetailList：{}", workOrderDetailList);
    }

    @Test
    public void WorkOrderInfo() {
        //查询
        //List<WorkOrderInfo>getList=iWorkOrderInfoService.getList();
        //log.info("getList：{}",getList);

        //根据派工单id查询
        List<WorkOrderInfo> getListById = iWorkOrderInfoService.getList("1");
        log.info("getListById：{}", getListById);

        LocalDate endTime = LocalDate.of(2021, 1, 15);
        LocalDate startTime = LocalDate.of(2020, 12, 31);
        //根据时间范围查询
//        List<WorkOrderInfo>getListByCreateTime=iWorkOrderInfoService.getList(startTime,endTime);
//        log.info("getListByCreateTime：{}",getListByCreateTime);
    }


}
