package com.comtrade.controller;

import com.comtrade.model.games.OnePlayerGame;
import com.comtrade.model.games.OnePlayerInitResponse;
import com.comtrade.model.games.RangListResponse;
import com.comtrade.service.gameservice.GameServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class Gamecontroler {

    private final GameServiceImpl gameservice;

    public Gamecontroler(GameServiceImpl gameservice ) {
        this.gameservice = gameservice;
    }

    @GetMapping("/OnePlayer/init")
    public OnePlayerInitResponse init(Principal principal){
        try {
            return gameservice.getOnePlayerGameInitData(principal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/GetRangList")
    public ResponseEntity<List<RangListResponse>> getRangList(){
        List<OnePlayerGame> listOfGames=gameservice.getTopTenOnePlayerGames();
        List<RangListResponse> rangListResponses=new ArrayList<>();
        for (OnePlayerGame game:   listOfGames) {
            rangListResponses.add(new RangListResponse(game));
        }
        if (rangListResponses.size()<=10){
            return ResponseEntity.ok().body(rangListResponses);
        }
        else {
            return ResponseEntity.ok().body(rangListResponses.subList(0,10));
        }
    }

    @GetMapping("OnePlayer/finishGame")
    public String newOnePlayerGame(Principal principal) throws Exception {
        gameservice.finishOnePlayerGame(principal);
        return "chooseGameOnePlayer.html";
    }

    @GetMapping("/queue")
    public ResponseEntity<Boolean> queue(Principal principal) {
        return ResponseEntity.ok(gameservice.addPlayerToQueue(principal));
    }

    @GetMapping("/dequeue")
    public ResponseEntity<Boolean> dequeue(Principal principal) {
        return ResponseEntity.ok(gameservice.removePlayerFromQueue(principal));
    }

    @GetMapping("/isInGame")
    public ResponseEntity<Boolean> isInGame(Principal principal) {
        return ResponseEntity.ok(gameservice.isInGame(principal));
    }


}
