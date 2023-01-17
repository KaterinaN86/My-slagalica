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
@CrossOrigin
public class AsocijacijaController {
    private final AsocijacijaServiceImpl asocijacijaService;

    public AsocijacijaController(AsocijacijaServiceImpl asocijacijaService) {
        this.asocijacijaService = asocijacijaService;
    }

    @GetMapping("/asocijacija/play")
    public ResponseEntity<Response> createNewAsocijacijaGame(Principal principal) throws Exception {
        if(!asocijacijaService.isActiveGame(principal)){
            return ResponseEntity.notFound().build();
        }
        return asocijacijaService.getInitData(principal);
    }

    @GetMapping("/asocijacija/getField/{fieldName}")
    public ResponseEntity<Response> getFieldValue(@PathVariable String fieldName, Principal principal) {
        return asocijacijaService.getValueOfSpecificField(fieldName, principal);
    }

    @PostMapping("/asocijacija/submitWord")
    public ResponseEntity<Response> submitWord(@RequestBody SubmitFieldValue submit, Principal principal){
        return asocijacijaService.checkSubmittedWord(submit.getGameId(), submit.getFieldName(), submit.getWord(),principal);
    }

    //Only for testing purpose
    //To check its imposible to get final word when game is active
    @GetMapping("/asocijacija/change/{gameId}")
    public void change(@PathVariable Long gameId, Principal principal){
        asocijacijaService.change(gameId, principal);
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
