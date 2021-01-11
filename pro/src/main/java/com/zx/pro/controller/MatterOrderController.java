package com.zx.pro.controller;

import com.zx.pro.service.IMatterOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/matterOrder")
public class MatterOrderController {

    @Resource
    private IMatterOrderService iMatterOrderService;

    @PostMapping()

}
