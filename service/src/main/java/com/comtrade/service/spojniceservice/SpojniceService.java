package com.comtrade.service.spojniceservice;


import com.comtrade.model.spojnicemodel.PairsModel;
import com.comtrade.model.spojnicemodel.SpojniceGame;

import java.security.Principal;


public interface SpojniceService {

    SpojniceGame getGame(Principal principal) throws Exception;

    SpojniceGame createNewSpojniceGame(Principal principal);

    PairsModel getRandomPairsModel();

    PairsModel getWords();

}