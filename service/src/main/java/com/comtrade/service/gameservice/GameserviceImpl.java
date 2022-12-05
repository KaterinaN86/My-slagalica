package com.comtrade.service.gameservice;

import com.comtrade.model.OnePlayerGame;
import com.comtrade.repository.gamerepository.Gamerepository;

import java.security.Principal;

public class GameserviceImpl implements Gameservice {

    private final Gamerepository gamerepository;

    public GameserviceImpl(Gamerepository gamerepository) {
        this.gamerepository = gamerepository;
    }

    @Override
    public OnePlayerGame createNewGame() {
        return null;
    }

    @Override
    public OnePlayerGame getGame(Principal principal) {
        return null;
    }
}
