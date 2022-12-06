package com.comtrade.service.gameservice;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.OnePlayerGame.OnePlayerInitResponse;

import java.security.Principal;

public interface GameService {
    OnePlayerGame createNewGame(Principal principal) throws Exception;
    OnePlayerGame getGame(Principal principal) throws Exception;
    OnePlayerInitResponse getInitData(Principal principal) throws Exception;
}
