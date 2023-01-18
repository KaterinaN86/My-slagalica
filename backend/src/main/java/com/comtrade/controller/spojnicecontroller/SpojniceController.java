package com.comtrade.controller.spojnicecontroller;

import com.comtrade.service.spojniceservice.SpojniceServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * SpojniceController
 *
 * @author tlukovic
 *  <p>This is <b>the controller</b> for the spojnice game</p>
 *  When the game is active, the player is presenter with a list of 8 pairs
 *  After he finished the game or the timer stops the number of
 *  points is shown to player
 *
 */

@RestController
@RequestMapping("/spojnice")
@CrossOrigin
public class SpojniceController {

    private final SpojniceServiceImpl spojniceServiceImpl;

    /**
     * @param spojniceServiceImpl default constructor
     */
    public SpojniceController(SpojniceServiceImpl spojniceServiceImpl) {
        this.spojniceServiceImpl = spojniceServiceImpl;
    }

    /**
     * @param principal the Spring Security principal
     * @return the created {@code ResponseEntity}
     * @throws Exception throws the IOException
     */
    @GetMapping("/start")
    public ResponseEntity<List<String>> getWords(Principal principal) throws Exception {
        if(!spojniceServiceImpl.isActiveGame(principal)){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(spojniceServiceImpl.getWords(principal), HttpStatus.OK);
    }

    /**
     * @param principal represents an object of the Spring Security principal
     * @param json maps the request body to json
     * @return getNumberOfPoints the number of points scored during one game
     * @throws Exception throws specific exception
     */
    @PostMapping("/submit")
    public int getNumOfPoints(Principal principal,@RequestBody String json) throws Exception{
        return spojniceServiceImpl.getNumberOfPoints(principal, json);
    }
}
