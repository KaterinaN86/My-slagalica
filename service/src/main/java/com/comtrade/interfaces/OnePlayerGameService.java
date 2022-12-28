package com.comtrade.interfaces;

import com.comtrade.model.games.OnePlayerGame;
import com.comtrade.model.games.OnePlayerInitResponse;

import java.security.Principal;
import java.util.List;

public interface OnePlayerGameService {
    OnePlayerGame createNewGame(Principal principal) throws Exception;
    OnePlayerGame getGame(Principal principal) throws Exception;
    OnePlayerInitResponse getInitData(Principal principal) throws Exception;
    List<OnePlayerGame> getTopTen();
}
