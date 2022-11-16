package com.comtrade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/skocko")
    public String SkockoIndex(){
        return "skocko.html";
    }
}
