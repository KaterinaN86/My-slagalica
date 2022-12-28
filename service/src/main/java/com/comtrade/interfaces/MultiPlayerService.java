package com.comtrade.interfaces;

import java.security.Principal;

public interface MultiPlayerService {

    String addPlayerToQueue(Principal principal);
    void createTwoPlayerGame(Principal principal);
}
