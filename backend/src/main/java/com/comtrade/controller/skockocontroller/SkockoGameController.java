package com.comtrade.controller.skockocontroller;

import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.responses.Response;
import com.comtrade.model.skockomodel.SkockoGame;
import com.comtrade.model.skockomodel.SkockoResponse;
import com.comtrade.model.skockomodel.SkockoSubmit;
import com.comtrade.service.skockoservice.SkockoGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/skocko")
public class SkockoGameController {

    private final SkockoGameService skockoGameService;

    public SkockoGameController(SkockoGameService skockoGameService) {
        this.skockoGameService = skockoGameService;
    }

    @GetMapping("/play")
    @CrossOrigin
    public ResponseEntity<SkockoGame> getNewGame(Principal principal) throws GameNotFoundException {
        if(!skockoGameService.isActiveGame(principal)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(skockoGameService.getInitData(principal));
    }

    @PostMapping("/submit")
    @CrossOrigin
    public ResponseEntity<SkockoResponse> submitCombination(@RequestBody SkockoSubmit submit,Principal principal) throws GameNotFoundException {
        return skockoGameService.handleSubmit(submit,principal);
    }

    @GetMapping("/getCombination")
    @CrossOrigin
    public ResponseEntity<List<Integer>> getCombination(Principal principal) throws GameNotFoundException {
        return skockoGameService.getCombination(principal);
    }
    @PutMapping("/finishGame")
    public ResponseEntity<Response> finishGame(Principal principal) throws GameNotFoundException {
        return skockoGameService.finishGame(principal);
    }
}
