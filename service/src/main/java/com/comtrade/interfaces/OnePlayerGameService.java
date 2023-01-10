package com.comtrade.interfaces;

import com.comtrade.model.games.OnePlayerGame;
import com.comtrade.model.games.OnePlayerInitResponse;

import java.security.Principal;
import java.util.List;

public interface OnePlayerGameService {
    OnePlayerGame createNewOnePlayerGame(Principal principal) throws Exception;
    OnePlayerGame getOnePlayerGame(Principal principal) throws Exception;
    OnePlayerInitResponse getOnePlayerGameInitData(Principal principal) throws Exception;
    List<OnePlayerGame> getTopTenOnePlayerGames();
    void finishOnePlayerGame(Principal principal) throws Exception;
}
