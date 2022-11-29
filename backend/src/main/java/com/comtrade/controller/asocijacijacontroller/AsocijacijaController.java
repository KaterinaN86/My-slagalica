package com.comtrade.controller.asocijacijacontroller;

import com.comtrade.model.asocijacijamodel.Response;
import com.comtrade.model.asocijacijamodel.SubmitFieldName;
import com.comtrade.model.asocijacijamodel.SubmitFieldValue;
import com.comtrade.service.asocijacijaservice.AsocijacijaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AsocijacijaController {
    private final AsocijacijaServiceImpl asocijacijaService;

    public AsocijacijaController(AsocijacijaServiceImpl asocijacijaService) {
        this.asocijacijaService = asocijacijaService;
    }

    @GetMapping("/asocijacija/play")
    public ResponseEntity<Response> createNewAsocijacijaGame(){
        return asocijacijaService.createNewAsocijacijaGame();
    }

    @GetMapping("/asocijacija/getField")
    public ResponseEntity<Response> getFieldValue(@RequestBody SubmitFieldName submit) {
        return asocijacijaService.getValueOfSpecificField(submit.getGameId(), submit.getFieldName());
    }

    @PostMapping("/asocijacija/submitWord")
    public ResponseEntity<Response> submitWord(@RequestBody SubmitFieldValue submit){
        return asocijacijaService.checkSubmittedWord(submit.getGameId(), submit.getFieldName(), submit.getWord());
    }

    //Only for testing purpose
    //To check its imposible to get final word when game is active
    @GetMapping("/asocijacija/change/{gameId}")
    public void change(@PathVariable Long gameId){
        asocijacijaService.change(gameId);
    }

}
