package com.comtrade.interfaces;

import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.exceptions.UserNotFoundException;
import com.comtrade.model.games.TwoPlayerInitResponse;

import java.security.Principal;

public interface MultiPlayerService {

    boolean addPlayerToQueue(Principal principal) throws UserNotFoundException;
    boolean removePlayerFromQueue(Principal principal) throws UserNotFoundException;
    void createTwoPlayerGame();
    boolean isInGame(Principal principal);
    TwoPlayerInitResponse getTwoPlayerInitData(Principal principal) throws GameNotFoundException;
}
