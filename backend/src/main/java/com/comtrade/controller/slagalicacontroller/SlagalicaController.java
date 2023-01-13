package com.comtrade.controller.slagalicacontroller;

import com.comtrade.model.koznaznamodel.responses.Response;
import com.comtrade.model.slagalicamodel.LettersResponse;
import com.comtrade.model.slagalicamodel.SlagalicaGame;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.model.slagalicamodel.SubmitResponse;
import com.comtrade.service.slagalicaservice.SlagalicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/slagalica")
public class SlagalicaController {

    private final SlagalicaService slagalicaService;

    public SlagalicaController(SlagalicaService slagalicaService) {
        this.slagalicaService = slagalicaService;
    }


    @RequestMapping("/play")
    @CrossOrigin
    public ResponseEntity<LettersResponse> getNewGame(Principal principal) throws Exception {
        if(!slagalicaService.isActiveGame(principal)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(slagalicaService.saveLetterForFindingWords(principal));
    }

    @PostMapping("/wordSubmit")
    @CrossOrigin
    public SubmitResponse slagalicaGame(@RequestBody SlagalicaUserWordSubmit slagalicaUserWordSubmit, Principal principal) throws Exception {
        return slagalicaService.userWordProcessing(slagalicaUserWordSubmit, principal);
    }
    @PutMapping("/finishGame")
    public ResponseEntity finishGame(Principal principal) throws Exception {
        return slagalicaService.finishGame(principal);
    }
}
