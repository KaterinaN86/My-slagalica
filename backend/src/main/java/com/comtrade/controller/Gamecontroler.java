package com.comtrade.controller;

import com.comtrade.model.OnePlayerGame.OnePlayerInitResponse;
import com.comtrade.service.gameservice.GameServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/OnePlayer")
public class Gamecontroler {
    private final GameServiceImpl gameservice;

    public Gamecontroler(GameServiceImpl gameservice) {
        this.gameservice = gameservice;
    }

    @GetMapping("/init")
    @CrossOrigin
    public OnePlayerInitResponse init(Principal principal){
        try {
            return gameservice.getInitData(principal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
