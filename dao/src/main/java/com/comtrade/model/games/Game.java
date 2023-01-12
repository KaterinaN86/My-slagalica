package com.comtrade.model.games;

import com.comtrade.model.Games;
import com.comtrade.model.IsActive;
import com.comtrade.model.Points;
import com.comtrade.model.Timers;

import java.security.Principal;

public interface Game {

     Points getPoints(Principal principal);
     void setPoints(Principal principal, Points points);

     Timers getTimers(Principal principal);
     void setTimers(Principal principal, Timers timers);

    IsActive getIsActive(Principal principal);
    void setIsActive(Principal principal, IsActive isActive);

    Games getGames();
    void setGames(Games games);

    void setFinished(Boolean finished);
}
