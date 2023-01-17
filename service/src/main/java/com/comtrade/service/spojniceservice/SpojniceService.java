package com.comtrade.service.spojniceservice;


import com.comtrade.model.spojnicemodel.PairsModel;
import com.comtrade.model.spojnicemodel.SpojniceGame;

import java.security.Principal;
import java.util.List;


public interface SpojniceService {

    SpojniceGame getGame(Principal principal) throws Exception;

    SpojniceGame createNewSpojniceGame(Principal principal) throws Exception;

    PairsModel getRandomPairsModel();

    List<String> getInitData(Principal principal) throws Exception;

}