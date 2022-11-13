package com.comtrade.backend.controllers;

import com.comtrade.backend.services.SlagalicaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SlagalicaController {

    private final SlagalicaService slagalicaService;

    public SlagalicaController(SlagalicaService slagalicaService) {
        this.slagalicaService = slagalicaService;
    }

    @GetMapping("/slovaZaPretragu")
    public String slovaZaPretraguReci(Model model) {
        String slova = slagalicaService.slovaZaPronalazakReci();
        model.addAttribute("slovaPretraga", slova);
        System.out.println(slova);
        return "slagalica";
    }

    @PostMapping("/slagalicaResenje")
    public Integer slagalicaGame(@RequestParam("chosenUserWord") String chosenUserWord) {
        System.out.println(chosenUserWord);
        System.out.println(slagalicaService.obradaFajla(slagalicaService.slovaZaPronalazakReci(),chosenUserWord));
        return slagalicaService.obradaFajla(slagalicaService.slovaZaPronalazakReci(),chosenUserWord);


    }

}
