package com.comtrade.service.mojbrojservice;


import com.comtrade.model.mojbrojmodel.MojBrojGame;

public interface MojBrojService {
    MojBrojGame createNewGame();
    boolean validateBrackets();
    String evaluate(String expression);
    Integer userSolutionDiff(String expression, long gameId) throws Exception;

}
