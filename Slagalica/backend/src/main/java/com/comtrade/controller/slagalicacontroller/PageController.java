package com.comtrade.controller.slagalicacontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping({"","/","/game"})
    public String showGame() {
        return "koznazna";
    }

}
