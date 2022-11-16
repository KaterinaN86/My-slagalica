package com.comtrade.service.skockoservice;

import com.comtrade.model.skockomodel.SkockoGame;
import com.comtrade.model.skockomodel.SkockoResponse;
import com.comtrade.model.skockomodel.SkockoSubmit;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface SkockoGameService {
    SkockoGame createNewGame() throws NoSuchAlgorithmException;
    ResponseEntity<SkockoResponse> handleSubmit(SkockoSubmit submit);
    ResponseEntity<List<Integer>> getCombination(Long id);
    boolean isWinningCombination(List<Integer> winningCombination, List<Integer> submittedCombination);
    int getNumberOfCorrectlyPlacedSymbolsInCombination(List<Integer> winningCombination, List<Integer> submittedCombination);

    int getNumberOfMisplacedSymbolsInCombination(List<Integer> winningCombination, List<Integer> submittedCombinatio);

    Integer numberOfPoints(Integer numberOfAttempts);
}
