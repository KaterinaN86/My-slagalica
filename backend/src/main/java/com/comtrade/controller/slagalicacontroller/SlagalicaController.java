package com.comtrade.controller.slagalicacontroller;

import com.comtrade.model.slagalicamodel.Slagalica;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.service.slagalicaservice.SlagalicaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/slagalica")
public class SlagalicaController {

    private final SlagalicaService slagalicaService;

    public SlagalicaController(SlagalicaService slagalicaService) {
        this.slagalicaService = slagalicaService;
    }


    @RequestMapping("/play")
    @CrossOrigin
    public Slagalica getNewGame(){
        return slagalicaService.saveLetterForFindingWords();
    }

    @PostMapping("/wordSubmit")
    @CrossOrigin
    public Integer slagalicaGame(@RequestBody SlagalicaUserWordSubmit slagalicaUserWordSubmit) {
        return slagalicaService.userWordProcessing(slagalicaUserWordSubmit);
    }

}