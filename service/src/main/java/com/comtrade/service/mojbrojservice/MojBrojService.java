package com.comtrade.service.mojbrojservice;


import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitRequest;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitResponse;
import org.springframework.http.ResponseEntity;

import javax.script.ScriptException;
import java.security.Principal;
import java.util.ArrayList;

public interface MojBrojService {
    MojBrojGame getGame(Principal principal) throws Exception;
    MojBrojGame createNewGame();
    MojBrojGame createNewGame(Long id, ArrayList<Integer> nums,Boolean isActive,String solution);
    boolean validateExpression(String expr, long gameId);
    Integer eval(String expr) throws ScriptException;
    Integer userSolutionDiff(String expression, Principal principal) throws Exception;
    String getSolution(Principal principal) throws Exception;
    MojBrojSubmitResponse submit(MojBrojSubmitRequest request, Principal principal);
    ResponseEntity finishGame(Principal principal) throws Exception;
    MojBrojGame getInitData(Principal principal) throws Exception;

}
