package com.comtrade.controller.skockocontroller;

import com.comtrade.model.skockomodel.SkockoGame;
import com.comtrade.model.skockomodel.SkockoResponse;
import com.comtrade.model.skockomodel.SkockoSubmit;
import com.comtrade.service.skockoservice.SkockoGameServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skocko")
public class SkockoGameController {

    private final SkockoGameServiceImpl skockoGameService;

    public SkockoGameController(SkockoGameServiceImpl skockoGameService) {
        this.skockoGameService = skockoGameService;
    }

    @GetMapping("/play")
    @CrossOrigin
    public SkockoGame getNewGame(){
        return skockoGameService.createNewGame();
    }

    @PostMapping("/submit")
    @CrossOrigin
    public ResponseEntity<SkockoResponse> submitCombination(@RequestBody SkockoSubmit submit){
        return skockoGameService.handleSubmit(submit);
    }

    @GetMapping("/getCombination/{gameId}")
    @CrossOrigin
    public ResponseEntity<List<Integer>> getCombination(@PathVariable Long gameId){
        return skockoGameService.getCombination(gameId);
    }
}
