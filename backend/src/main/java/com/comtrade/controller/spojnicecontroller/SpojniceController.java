package com.comtrade.controller.spojnicecontroller;

import com.comtrade.service.spojniceservice.SpojniceServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/spojnice")
@CrossOrigin
public class SpojniceController {

    private final SpojniceServiceImpl spojniceServiceImpl;

    public SpojniceController(SpojniceServiceImpl spojniceServiceImpl) {
        this.spojniceServiceImpl = spojniceServiceImpl;
    }

    @GetMapping("/start")
    public ResponseEntity<List<String>> getWords(Principal principal) throws Exception {
        return new ResponseEntity<>(spojniceServiceImpl.getWords(principal), HttpStatus.OK);
    }

    @PostMapping("/submit")
    public int numberofPoints(Principal principal,@RequestBody String json) throws Exception{
        System.out.println(json);
        return spojniceServiceImpl.getNumberOfPoints(principal);
    }
}
