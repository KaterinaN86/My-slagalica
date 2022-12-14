package com.comtrade.controller.slagalicacontroller;

import com.comtrade.model.slagalicamodel.LettersResponse;
import com.comtrade.model.slagalicamodel.SlagalicaGame;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.service.slagalicaservice.SlagalicaService;
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
    public LettersResponse getNewGame(Principal principal){
        return slagalicaService.saveLetterForFindingWords(principal);
    }

    @PostMapping("/wordSubmit")
    @CrossOrigin
    public Integer slagalicaGame(@RequestBody SlagalicaUserWordSubmit slagalicaUserWordSubmit, Principal principal) {
        return slagalicaService.userWordProcessing(slagalicaUserWordSubmit, principal);
    }

}
