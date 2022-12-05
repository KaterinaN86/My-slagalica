package com.comtrade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/OnePlayer")
public class Gamecontroler {


    @GetMapping()
    public String onePlayer(){
        return "chooseGameOnePlayer.html";
    }
    @GetMapping("/init")
    public String init(Principal principal){
        System.out.println(principal.getName());
        return "asdf";
    }
}
