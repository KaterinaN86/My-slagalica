package com.comtrade.service.spojniceservice;


import com.comtrade.model.spojnicemodel.SpojniceGame;
import org.springframework.stereotype.Service;
import java.security.Principal;


@Service
public interface SpojniceService {



    SpojniceGame getGame(Principal principal) throws Exception;

    SpojniceGame createNewSpojniceGame();

    int getRandomPairsModel();

}