package com.comtrade.controller.mojbrojcontroller;

import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitRequest;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitResponse;
import com.comtrade.model.skockomodel.SkockoGame;
import com.comtrade.service.mojbrojservice.MojBrojService;
import com.comtrade.service.mojbrojservice.MojBrojServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<MojBrojSubmitResponse> submit(@RequestBody MojBrojSubmitRequest submit){
        //todo check if user expresion is valid
        Integer diff=null;
        Integer numOfPoints=0;
        try {
            diff=mojBrojService.userSolutionDiff(submit.getExpression(),submit.getGameId());
        } catch (Exception e) {
            return ResponseEntity.ok().body(new MojBrojSubmitResponse(e.getMessage(),"1+2",0));
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
        return ResponseEntity.ok().body(new MojBrojSubmitResponse("","1+2", numOfPoints));
    }

}
