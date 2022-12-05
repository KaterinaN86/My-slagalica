package com.comtrade.service.gameservice;

import com.comtrade.model.OnePlayerGame;

import java.security.Principal;

public interface Gameservice {
    OnePlayerGame createNewGame();
    OnePlayerGame getGame(Principal principal);

}
