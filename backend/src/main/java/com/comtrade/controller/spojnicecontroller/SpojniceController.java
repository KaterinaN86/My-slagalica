package com.comtrade.controller.spojnicecontroller;

import com.comtrade.model.asocijacijamodel.ResponseWithGameId;
import com.comtrade.model.spojnicemodel.SpojniceGame;
import com.comtrade.service.spojniceservice.SpojniceServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/spojnice")
public class SpojniceController {
    private final SpojniceServiceImpl spojniceServiceImpl;

    public SpojniceController(SpojniceServiceImpl spojniceServiceImpl) {
        this.spojniceServiceImpl = spojniceServiceImpl;
    }

    @GetMapping("/start")
    @CrossOrigin
    public List<String> getWords(Principal principal) throws Exception {
        return spojniceServiceImpl.getWords(principal);
    }

    @GetMapping("/points")
    @CrossOrigin
    public int numberofPoints(Principal principal) throws Exception{
        return spojniceServiceImpl.getNumberOfPoints(principal);
    }
}
