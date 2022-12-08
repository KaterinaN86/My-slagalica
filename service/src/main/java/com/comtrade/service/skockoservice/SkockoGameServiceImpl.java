package com.comtrade.service.skockoservice;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.skockomodel.*;
import com.comtrade.repository.gamerepository.Gamerepository;
import com.comtrade.repository.skockorepository.SkockoGameRepository;
import com.comtrade.service.gameservice.GameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
public class SkockoGameServiceImpl implements SkockoGameService{

    private final SkockoGameRepository skockoGameRepository;
    private final Gamerepository gamerepository;

    @Autowired
    private GameServiceImpl gameService;

    public SkockoGameServiceImpl(SkockoGameRepository skockoGameRepository, Gamerepository gamerepository) {
        this.skockoGameRepository = skockoGameRepository;
        this.gamerepository = gamerepository;
    }

    @Override
    public SkockoGame getGame(Principal principal) {
        OnePlayerGame game=null;
        try {
            game=gameService.getGame(principal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (game.getSkockoGame()!=null){
            return game.getSkockoGame();
        }
        else{
            SkockoGame Sgame=createNewGame();
            game.setSkockoGame(Sgame);
            gamerepository.save(game);
            return game.getSkockoGame();
        }

    }

    @Override
    public SkockoGame createNewGame(){
        log.info("Creating new instance of Skocko game");
        SkockoGame newGame = new SkockoGame();
        skockoGameRepository.save(newGame);
        log.info("Created new instance of Skocko game: " + newGame);
        return newGame;
    }
    @Override
    public ResponseEntity<SkockoResponse> handleSubmit(SkockoSubmit submit,Principal principal) {
        boolean isWinningCombination;
        SkockoResponse skockoResponse;
        OnePlayerGame game=null;
        try {
            game=gameService.getGame(principal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        SkockoGame Sgame=game.getSkockoGame();

        log.info("Processing submit for game: " + submit.getGameId() +". Submitted combination: " + submit.getCombination());

        isWinningCombination = isWinningCombination(Sgame.getCombination(), submit.getCombination());

        if(isWinningCombination){
            int numOfPoints=numberOfPoints(submit.getAttempt());
            if (Sgame.isActive()){
                Sgame.setNumOfPoints(numOfPoints);
                Sgame.setActive(false);
                game.setNumOfPoints(game.getNumOfPoints()+numOfPoints);
                gamerepository.save(game);
            }

            skockoResponse = new SkockoResponseWithNumberOfPoints(isWinningCombination,numOfPoints, Sgame.getCombination());
            log.info("Returning object" + skockoResponse + "as response for submitting winning combination" + " for game id: " + Sgame.getId());
            return ResponseEntity.ok()
                    .body(skockoResponse);
        }

        skockoResponse = new SkockoResponseWithPositions(isWinningCombination, getNumberOfCorrectlyPlacedSymbolsInCombination(Sgame.getCombination(), submit.getCombination()), getNumberOfMisplacedSymbolsInCombination(Sgame.getCombination(), submit.getCombination()));
        log.info("Returning object" + skockoResponse + "as response for submitting non-winning combination" + " for game id: " + Sgame.getId());
        return ResponseEntity.ok()
                .body(skockoResponse);
    }
    @Override
    public boolean isWinningCombination(List<Integer> winningCombination, List<Integer> submittedCombination) {
        return submittedCombination.equals(winningCombination);
    }
    @Override
    public  int getNumberOfCorrectlyPlacedSymbolsInCombination(List<Integer> winningCombination, List<Integer> submittedCombination){
        int n=0;
        for(int i=0; i<4; i++){
            if(submittedCombination.get(i).equals(winningCombination.get(i)))
                n++;
        }
        return n;
    }
    public int getNumberOfMisplacedSymbolsInCombination(List<Integer> winningCombination, List<Integer> submittedCombinatio){
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
    @Override
    public Integer numberOfPoints(Integer numberOfAttempts){
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
    @Override
    public ResponseEntity<List<Integer>> getCombination(Principal principal){
        OnePlayerGame game=null;
        try {
            game=gameService.getGame(principal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SkockoGame Sgame=game.getSkockoGame();
        Sgame.setActive(false);

        log.info("Returning combination for game id: " + Sgame.getId());
        return ResponseEntity.ok().body(Sgame.getCombination());

    }
}
