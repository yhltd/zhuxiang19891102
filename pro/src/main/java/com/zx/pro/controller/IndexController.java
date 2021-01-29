package com.zx.pro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dai
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
