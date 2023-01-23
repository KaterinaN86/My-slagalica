package com.comtrade.interfaces;

import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.exceptions.UserNotFoundException;
import com.comtrade.model.games.Game;
import com.comtrade.model.games.OnePlayerGame;
import com.comtrade.model.games.OnePlayerInitResponse;

import java.security.Principal;
import java.util.List;

public interface OnePlayerGameService {
    OnePlayerGame createNewOnePlayerGame(Principal principal) throws UserNotFoundException;
    Game getGame(Principal principal) throws GameNotFoundException;
    OnePlayerInitResponse getOnePlayerGameInitData(Principal principal) throws UserNotFoundException;
    List<OnePlayerGame> getTopTenOnePlayerGames();
}
