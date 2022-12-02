package com.comtrade.service.mojbrojservice;


import com.comtrade.model.mojbrojmodel.MojBrojGame;

import javax.script.ScriptException;
import java.util.ArrayList;

public interface MojBrojService {
    MojBrojGame createNewGame();
    MojBrojGame createNewGame(Long id, ArrayList<Integer> nums,Boolean isActive,String solution);
    boolean validateExpression(String expr, long gameId);
    Integer eval(String expr) throws ScriptException;
    Integer userSolutionDiff(String expression, long gameId) throws Exception;
    String getSolution(Long gameid);

}
