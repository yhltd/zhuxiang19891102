package com.zx.pro;

import com.zx.pro.entity.MatterInfo;
import com.zx.pro.entity.MatterProduct;
import com.zx.pro.entity.ProjectInfo;
import com.zx.pro.service.IMatterInfoService;
import com.zx.pro.service.IProjectInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    /**
     * 测试插入项目信息
     */
    @Test
    public void ProjectInfoTest(){
        //项目集合
        List<ProjectInfo> projectInfoList = iProjectInfoService.getList();
        log.info("projectInfoList：{}",projectInfoList);

        //新项目
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectName("测试项目1");
        projectInfo.setCustomerName("测试客户1");
        projectInfo.setProjectAddress("测试项目地址1");
        projectInfo.setCreateTime(LocalDateTime.now());

        //物料集合
        List<MatterInfo> matterInfoList = iMatterInfoService.getList();
        log.info("matterInfoList：{}",matterInfoList);

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
        log.info("matterProductList：{}",matterProductList);

        //插入项目信息
        boolean result = iProjectInfoService.add(projectInfo,matterProductList);
        log.info("是否插入成功：{}",result);
    }
}
