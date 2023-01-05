package com.comtrade.interfaces;

import java.security.Principal;

public interface MultiPlayerService {

    boolean addPlayerToQueue(Principal principal);
    boolean removePlayerFromQueue(Principal principal);
    void createTwoPlayerGame();
    boolean isInGame(Principal principal);
}
