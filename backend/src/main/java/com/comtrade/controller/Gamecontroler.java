package com.comtrade.controller;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.OnePlayerGame.OnePlayerInitResponse;
import com.comtrade.service.gameservice.GameserviceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/OnePlayer")
public class Gamecontroler {
    private final GameserviceImpl gameservice;

    public Gamecontroler(GameserviceImpl gameservice) {
        this.gameservice = gameservice;
    }

    @GetMapping("/init")
    @CrossOrigin
    public OnePlayerInitResponse init(Principal principal){//todo change response type

        try {
            return gameservice.getInitData(principal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
