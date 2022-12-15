package com.comtrade.controller.asocijacijacontroller;

import com.comtrade.model.asocijacijamodel.Response;
import com.comtrade.model.asocijacijamodel.SubmitFieldName;
import com.comtrade.model.asocijacijamodel.SubmitFieldValue;
import com.comtrade.model.asocijacijamodel.SubmitNumberOfFields;
import com.comtrade.service.asocijacijaservice.AsocijacijaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class AsocijacijaController {
    private final AsocijacijaServiceImpl asocijacijaService;

    public AsocijacijaController(AsocijacijaServiceImpl asocijacijaService) {
        this.asocijacijaService = asocijacijaService;
    }

    @GetMapping("/asocijacija/play")
    @CrossOrigin
    public ResponseEntity<Response> createNewAsocijacijaGame(Principal principal){
        return asocijacijaService.createNewAsocijacijaGame(principal);
    }

    @GetMapping("/asocijacija/{gameId}/getField/{fieldName}")
    @CrossOrigin
    public ResponseEntity<Response> getFieldValue(@PathVariable Long gameId, @PathVariable String fieldName) {
        return asocijacijaService.getValueOfSpecificField(gameId, fieldName);
    }

    @PostMapping("/asocijacija/submitWord")
    @CrossOrigin
    public ResponseEntity<Response> submitWord(@RequestBody SubmitFieldValue submit, Principal principal){
        return asocijacijaService.checkSubmittedWord(submit.getGameId(), submit.getFieldName(), submit.getWord(),principal);
    }

    //Only for testing purpose
    //To check its imposible to get final word when game is active
    @GetMapping("/asocijacija/change/{gameId}")
    public void change(@PathVariable Long gameId){
        asocijacijaService.change(gameId);
    }

    @GetMapping("/asocijacija/getNumberOfPoints/{gameId}")
    public ResponseEntity<Response> getNumberOfPoints(@PathVariable Long gameId, Principal principal){
        return asocijacijaService.getNumberOfPoints(gameId,principal);
    }
    @PutMapping("/asocijacija/finishGame")
    public ResponseEntity<Response> finishGame(Principal principal) throws Exception {
        return asocijacijaService.finishGame(principal);
    }

}
