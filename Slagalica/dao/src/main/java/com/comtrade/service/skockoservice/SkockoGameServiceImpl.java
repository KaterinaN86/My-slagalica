package com.comtrade.service.skockoservice;

import com.comtrade.model.skockomodel.*;
import com.comtrade.repository.skockorepository.SkockoGameRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkockoGameServiceImpl {
    private final SkockoGameRepository skockoGameRepository;

    public SkockoGameServiceImpl(SkockoGameRepository skockoGameRepository) {
        this.skockoGameRepository = skockoGameRepository;
    }

    public SkockoGame createNewGame(){
        SkockoGame newGame = new SkockoGame();
        skockoGameRepository.save(newGame);
        System.out.println(newGame);
        return newGame;
    }
    public ResponseEntity<SkockoResponse> handleSubmit(SkockoSubmit submit) {
        Optional<SkockoGame> existingGame = skockoGameRepository.findById(submit.getGameId());
        if (existingGame.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        boolean isWinningCombination = isWinningCombination(existingGame.get().getCombination(), submit.getCombination());

        if(isWinningCombination){
            return ResponseEntity.ok()
                    .body(new SkockoResponseWithNumberOfPoints(isWinningCombination,numberOfPoints(submit.getAttempt()), existingGame.get().getCombination()));
        }
        return ResponseEntity.ok()
                .body(new SkockoResponseWithPositions(isWinningCombination,getNumberOfCorrectlyPlacedSymbolInCombination(existingGame.get().getCombination(), submit.getCombination()), getNumberOfMisplacedSymbolInCombination(existingGame.get().getCombination(), submit.getCombination()))); //ovo treba da koristi novu klasu za tacno/netacno i broj bodova
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
        Optional<SkockoGame> existingGame = skockoGameRepository.findById(id);
        if (existingGame.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }else{
            return ResponseEntity.ok()
                    .body(existingGame.get().getCombination());
        }

    }
}
