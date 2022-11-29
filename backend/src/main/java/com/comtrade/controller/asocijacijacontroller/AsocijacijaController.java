package com.comtrade.controller.asocijacijacontroller;

import com.comtrade.service.asocijacijaservice.AsocijacijaServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsocijacijaController {
    private final AsocijacijaServiceImpl asocijacijaService;

    public AsocijacijaController(AsocijacijaServiceImpl asocijacijaService) {
        this.asocijacijaService = asocijacijaService;
    }

    @GetMapping("/asocijacija/play")
    public Long createNewAsocijacijaGame(){
        return asocijacijaService.createNewAsocijacijaGame();
    }


}
