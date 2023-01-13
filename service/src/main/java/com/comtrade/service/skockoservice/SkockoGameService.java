package com.comtrade.service.skockoservice;


import com.comtrade.model.koznaznamodel.responses.Response;
import com.comtrade.model.skockomodel.SkockoGame;
import com.comtrade.model.skockomodel.SkockoResponse;
import com.comtrade.model.skockomodel.SkockoSubmit;
import org.springframework.http.ResponseEntity;


import java.security.Principal;
import java.util.List;

public interface SkockoGameService {

    SkockoGame getGame(Principal principal);
    SkockoGame createNewGame();
    ResponseEntity<SkockoResponse> handleSubmit(SkockoSubmit submit,Principal principal);
    ResponseEntity<List<Integer>> getCombination(Principal principal);
    boolean isWinningCombination(List<Integer> winningCombination, List<Integer> submittedCombination);
    int getNumberOfCorrectlyPlacedSymbolsInCombination(List<Integer> winningCombination, List<Integer> submittedCombination);

    int getNumberOfMisplacedSymbolsInCombination(List<Integer> winningCombination, List<Integer> submittedCombinatio);

    Integer numberOfPoints(Integer numberOfAttempts);

    ResponseEntity<Response> finishGame(Principal principal) throws Exception;

    boolean isActiveGame(Principal principal) throws Exception;
}
