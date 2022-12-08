package com.comtrade.controller;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.OnePlayerGame.OnePlayerInitResponse;
import com.comtrade.service.gameservice.GameServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    @GetMapping("/GetRangList")
    @CrossOrigin
    public ResponseEntity<List<OnePlayerGame>> getRangList(){
        List<OnePlayerGame> listOfGames=gameservice.getTopTen();
        List<OnePlayerGame> listOfGamesCopy=new ArrayList<>();
        for (int i=0;i<10;i++){
            try {
                listOfGamesCopy.add(listOfGames.get(i));
            }catch (Exception e){
                System.out.println("there is less than 10 finished games.");
                break;
            }
        }
        return ResponseEntity.ok().body(listOfGamesCopy);
    }
}
