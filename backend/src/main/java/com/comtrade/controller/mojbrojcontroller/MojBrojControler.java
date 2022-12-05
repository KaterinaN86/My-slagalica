package com.comtrade.controller.mojbrojcontroller;

import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitRequest;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitResponse;
import com.comtrade.service.mojbrojservice.MojBrojServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/MojBroj")
public class MojBrojControler {
    private final MojBrojServiceImpl mojBrojService;

    public MojBrojControler(MojBrojServiceImpl mojBrojService) {
        this.mojBrojService = mojBrojService;
    }

    @GetMapping("/Init")
    @CrossOrigin
    public MojBrojGame getNewGame(){
        return mojBrojService.createNewGame();
    }

    @PostMapping("/Submit")
    @CrossOrigin
    public ResponseEntity<MojBrojSubmitResponse> submit(@RequestBody MojBrojSubmitRequest submit, Principal principal){
        Integer diff=null;
        Integer numOfPoints=0;
        String expression=submit.getExpression();
        String user=principal.getName();//for furure use
        try {
            diff=mojBrojService.userSolutionDiff(expression,submit.getGameId());
        } catch (Exception e) {
            return ResponseEntity.ok().body(new MojBrojSubmitResponse(e.getMessage(),"",0));
        }
        switch (diff){
            case 0:
                numOfPoints=30;
                break;
            case 1:
                numOfPoints=20;
                break;
            case 2:
                numOfPoints=10;
                break;
        }
        String solution = mojBrojService.getSolution(submit.getGameId());
        return ResponseEntity.ok().body(new MojBrojSubmitResponse("",solution, numOfPoints));
    }

}
