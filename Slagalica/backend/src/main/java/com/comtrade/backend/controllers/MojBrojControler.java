package com.comtrade.backend.controllers;

import com.comtrade.backend.gameClasses.MojBroj;
import com.comtrade.backend.services.MojBrojService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MojBrojControler {
    @Autowired
    MojBrojService mojBrojService;

    @GetMapping
    @RequestMapping("/MojBroj")
    public String ShowMojBroj(Model model){
        MojBroj game=mojBrojService.generateGame();
        System.out.println(game);
        model.addAttribute("game",game);
        return "mojbroj";
    }
}
