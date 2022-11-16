package com.comtrade.controller.slagalicacontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/slagalica")
    public String showGame() {
        return "slagalica.html";
    }

}
