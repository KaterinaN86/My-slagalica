package com.comtrade.controller;

import com.comtrade.model.games.OnePlayerGame;
import com.comtrade.model.games.OnePlayerInitResponse;
import com.comtrade.model.games.RangListResponse;
import com.comtrade.service.gameservice.MultiPlayerServiceImpl;
import com.comtrade.service.gameservice.OnePlayerOnePlayerGameServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class Gamecontroler {

    private final OnePlayerOnePlayerGameServiceImpl gameservice;
    private final MultiPlayerServiceImpl multiPlayerService;

    public Gamecontroler(OnePlayerOnePlayerGameServiceImpl gameservice, MultiPlayerServiceImpl multiPlayerService) {
        this.gameservice = gameservice;
        this.multiPlayerService = multiPlayerService;
    }

    @GetMapping("/OnePlayer/init")
    public OnePlayerInitResponse init(Principal principal){
        try {
            return gameservice.getInitData(principal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/GetRangList")
    public ResponseEntity<List<RangListResponse>> getRangList(){
        List<OnePlayerGame> listOfGames=gameservice.getTopTen();
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
        gameservice.finishedGame(principal);
        return "chooseGameOnePlayer.html";
    }

    @GetMapping("/queue")
    public ResponseEntity<Boolean> queue(Principal principal) {
        return ResponseEntity.ok(multiPlayerService.addPlayerToQueue(principal));
    }

    @GetMapping("/dequeue")
    public ResponseEntity<Boolean> dequeue(Principal principal) {
        return ResponseEntity.ok(multiPlayerService.removePlayerFromQueue(principal));
    }

    @GetMapping("/isInGame")
    public ResponseEntity<Boolean> isInGame(Principal principal) {
        return ResponseEntity.ok(multiPlayerService.isInGame(principal));
    }


}
