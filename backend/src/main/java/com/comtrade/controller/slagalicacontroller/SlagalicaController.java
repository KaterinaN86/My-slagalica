package com.comtrade.controller.slagalicacontroller;

import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.model.slagalicamodel.LettersResponse;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.model.slagalicamodel.SubmitResponse;
import com.comtrade.responses.Response;
import com.comtrade.service.slagalicaservice.SlagalicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/slagalica")
public class SlagalicaController {

    private final SlagalicaService slagalicaService;

    public SlagalicaController(SlagalicaService slagalicaService) {
        this.slagalicaService = slagalicaService;
    }


    @RequestMapping("/play")
    @CrossOrigin
    public synchronized ResponseEntity<LettersResponse> getNewGame(Principal principal) throws GameNotFoundException {
        if(!slagalicaService.isActiveGame(principal)){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(slagalicaService.getInitData(principal));
        }
    }

    @PostMapping("/wordSubmit")
    @CrossOrigin
    public SubmitResponse slagalicaGame(@RequestBody SlagalicaUserWordSubmit slagalicaUserWordSubmit, Principal principal) throws GameNotFoundException  {
        return slagalicaService.userWordProcessing(slagalicaUserWordSubmit, principal);
    }
    @PutMapping("/finishGame")
    public ResponseEntity<Response> finishGame(Principal principal) throws GameNotFoundException  {
        return slagalicaService.finishGame(principal);
    }
}
