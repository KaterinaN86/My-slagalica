package com.comtrade.controller.mojbrojcontroller;

import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitRequest;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitResponse;
import com.comtrade.service.mojbrojservice.MojBrojServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/MojBroj")
public class MojBrojControler {
    private final MojBrojServiceImpl mojBrojService;

    public MojBrojControler(MojBrojServiceImpl mojBrojService) {
        this.mojBrojService = mojBrojService;
    }

    @GetMapping("/Init")
    @CrossOrigin
    public ResponseEntity<MojBrojGame> getNewGame(Principal principal){
        try {
            if(!mojBrojService.isActiveGame(principal)){
                return ResponseEntity.notFound().build();
            }
            return  ResponseEntity.ok().body(mojBrojService.getInitData(principal));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/Submit")
    @CrossOrigin
    public ResponseEntity<MojBrojSubmitResponse> submit(@RequestBody MojBrojSubmitRequest submit, Principal principal) {
        return ResponseEntity.ok().body(mojBrojService.submit(submit,principal));
    }
    @PutMapping("/finishGame")
    public ResponseEntity finishGame(Principal principal) throws Exception {
        return mojBrojService.finishGame(principal);
    }


}
