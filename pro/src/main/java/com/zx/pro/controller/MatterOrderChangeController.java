package com.zx.pro.controller;

import com.zx.pro.entity.MatterOrderChange;
import com.zx.pro.service.IMatterOrderChangeService;
import com.zx.pro.util.PowerUtil;
import com.zx.pro.util.ResultInfo;
import com.zx.pro.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/matter_order_change")
public class MatterOrderChangeController {


    @Autowired
    private IMatterOrderChangeService iMatterOrderChangeService;

    @RequestMapping("/post_list")
    public ResultInfo postList(HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("订单物料需求变化表")) {
                return ResultInfo.error(401, "无权限");
            }

            List<MatterOrderChange> list = iMatterOrderChangeService.postList();

            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("查询成功", list);
            } else {
                return ResultInfo.success("查询失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            return ResultInfo.error("查询失败");
        }
    }

    @RequestMapping("/select_list")
    public ResultInfo postList(@RequestBody HashMap map, HttpSession session) {
        try {
            PowerUtil powerUtil = PowerUtil.getPowerUtil(session);
            if (!powerUtil.isSelect("订单物料需求变化表")) {
                return ResultInfo.error(401, "无权限");
            }

            //物料编码
            String code = map.get("code").toString();

            List<MatterOrderChange> list = iMatterOrderChangeService.selectList(code);

            if (StringUtils.isNotNull(list)) {
                return ResultInfo.success("查询成功", list);
            } else {
                return ResultInfo.success("查询失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败：{}", e.getMessage());
            return ResultInfo.error("查询失败");
        }
    }
}
