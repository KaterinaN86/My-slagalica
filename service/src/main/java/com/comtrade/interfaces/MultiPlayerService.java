package com.comtrade.interfaces;

import com.comtrade.model.games.TwoPlayerInitResponse;

import java.security.Principal;

public interface MultiPlayerService {

    boolean addPlayerToQueue(Principal principal);
    boolean removePlayerFromQueue(Principal principal);
    void createTwoPlayerGame();
    boolean isInGame(Principal principal);
    TwoPlayerInitResponse getTwoPlayerInitData(Principal principal) throws Exception;
}
