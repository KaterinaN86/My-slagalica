package com.comtrade.service.gameservice;

import com.comtrade.model.OnePlayerGame;

import java.security.Principal;

public interface Gameservice {
    OnePlayerGame createNewGame(Principal principal) throws Exception;
    OnePlayerGame getGame(Principal principal) throws Exception;

}
