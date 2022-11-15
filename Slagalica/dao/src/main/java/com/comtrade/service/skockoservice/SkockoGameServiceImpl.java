package com.comtrade.service.skockoservice;

import com.comtrade.model.skockomodel.*;
import com.comtrade.repository.skockorepository.SkockoGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SkockoGameServiceImpl {

    private final SkockoGameRepository skockoGameRepository;

    public SkockoGameServiceImpl(SkockoGameRepository skockoGameRepository) {
        this.skockoGameRepository = skockoGameRepository;
    }

    public SkockoGame createNewGame(){
        log.info("Creating new instance of Skocko game");
        SkockoGame newGame = new SkockoGame();
        skockoGameRepository.save(newGame);
        log.info("Created new instance of Skocko game: " + newGame);
        return newGame;
    }
    public ResponseEntity<SkockoResponse> handleSubmit(SkockoSubmit submit) {
        boolean isWinningCombination;
        SkockoResponse skockoResponse;

        log.info("Processing submit for game: " + submit.getGameId() +". Submitted combination: " + submit.getCombination());
        Optional<SkockoGame> existingGame = skockoGameRepository.findById(submit.getGameId());
        if (existingGame.isEmpty()) {
            log.info("Game with id: " + submit.getGameId() + " not found !");
            return ResponseEntity.notFound()
                    .build();
        }

        isWinningCombination = isWinningCombination(existingGame.get().getCombination(), submit.getCombination());

        if(isWinningCombination){
            skockoResponse = new SkockoResponseWithNumberOfPoints(isWinningCombination,numberOfPoints(submit.getAttempt()), existingGame.get().getCombination());
            log.info("Returning object" + skockoResponse + "as response for submitting winning combination" + " for game id: " + existingGame.get().getId());
            return ResponseEntity.ok()
                    .body(skockoResponse);
        }

        skockoResponse = new SkockoResponseWithPositions(isWinningCombination, getNumberOfCorrectlyPlacedSymbolInCombination(existingGame.get().getCombination(), submit.getCombination()), getNumberOfMisplacedSymbolInCombination(existingGame.get().getCombination(), submit.getCombination()));
        log.info("Returning object" + skockoResponse + "as response for submitting non-winning combination" + " for game id: " + existingGame.get().getId());
        return ResponseEntity.ok()
                .body(skockoResponse);
    }

    public static boolean isWinningCombination(List<Integer> winningCombination, List<Integer> submittedCombination) {
        return submittedCombination.equals(winningCombination);
    }
    public static  int getNumberOfCorrectlyPlacedSymbolInCombination(List<Integer> winningCombination, List<Integer> submittedCombination){
        int n=0;
        for(int i=0; i<4; i++){
            if(submittedCombination.get(i).equals(winningCombination.get(i)))
                n++;
        }
        return n;
    }
    public static int getNumberOfMisplacedSymbolInCombination(List<Integer> winningCombination, List<Integer> submittedCombinatio){
        int n=0;
        int m=4;
        for(int i=0;i<m; i++){
            if(winningCombination.get(i).equals(submittedCombinatio.get(i))){
                winningCombination.remove(i);
                submittedCombinatio.remove(i);
                m--;
                i--;
            }
        }
        for(int i=0; i<m; i++){
            for(int j=0; j<m; j++){
                if(winningCombination.get(i).equals(submittedCombinatio.get(j))){
                    n++;
                    break;
                }
            }

        }
        return n;
    }

    public static Integer numberOfPoints(Integer numberOfAttempts){
        if(numberOfAttempts<0 || numberOfAttempts>5){//promjenio
            return 0;
        }else{
            if(numberOfAttempts<=4){//promjenio
                return 30;
            }else{
                return 20;
            }
        }
    }

    public ResponseEntity<List<Integer>> getCombination(Long id){
        log.info("Getting combination for game id: " + id);
        Optional<SkockoGame> existingGame = skockoGameRepository.findById(id);
        if (existingGame.isEmpty()) {
            log.info("Game with id: " + id + " not found !");
            return ResponseEntity.notFound()
                    .build();
        }else{
            log.info("Returning combination for game id: " + existingGame.get().getId());
            return ResponseEntity.ok()
                    .body(existingGame.get().getCombination());
        }
    }
}
