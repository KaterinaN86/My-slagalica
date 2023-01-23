package com.comtrade.service.mojbrojservice;


import com.comtrade.exceptions.BadExpressionException;
import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.exceptions.IllegalSubmitException;
import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitRequest;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitResponse;
import com.comtrade.responses.Response;
import org.springframework.http.ResponseEntity;

import javax.script.ScriptException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;

public interface MojBrojService {
    MojBrojGame getGame(Principal principal) throws GameNotFoundException;
    MojBrojGame createNewGame() throws NoSuchAlgorithmException;
    MojBrojGame createNewGame(Long id, List<Integer> nums, Boolean isActive, String solution);
    boolean validateExpression(String expr, long gameId);
    Integer eval(String expr) throws ScriptException;
    Integer userSolutionDiff(String expression, Principal principal) throws IllegalSubmitException, GameNotFoundException, BadExpressionException, ScriptException;
    String getSolution(Principal principal) throws GameNotFoundException;
    MojBrojSubmitResponse submit(MojBrojSubmitRequest request, Principal principal);
    ResponseEntity<Response> finishGame(Principal principal) throws GameNotFoundException;
    MojBrojGame getInitData(Principal principal) throws GameNotFoundException;

}
