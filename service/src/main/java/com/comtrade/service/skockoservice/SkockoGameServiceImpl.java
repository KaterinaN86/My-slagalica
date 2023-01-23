package com.comtrade.service.skockoservice;

import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.model.Timers;
import com.comtrade.model.games.Game;
import com.comtrade.responses.Response;
import com.comtrade.model.skockomodel.*;
import com.comtrade.repository.TimersRepository;
import com.comtrade.repository.skockorepository.SkockoGameRepository;
import com.comtrade.service.gameservice.GameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class SkockoGameServiceImpl implements SkockoGameService{

    private final SkockoGameRepository skockoGameRepository;
    private final TimersRepository timersRepository;

    @Autowired
    private GameServiceImpl gameService;

    public SkockoGameServiceImpl(SkockoGameRepository skockoGameRepository, TimersRepository timersRepository) {
        this.skockoGameRepository = skockoGameRepository;
        this.timersRepository = timersRepository;
    }

    @Override
    public SkockoGame getInitData(Principal principal) throws GameNotFoundException {
        Game game = gameService.getGame(principal);
        gameService.saveGame(game);
        Timers timers = game.getTimers(principal);
        timers.setStartTimeSkocko(LocalTime.now());
        timersRepository.save(timers);
        game.setTimers(principal, timers);
        gameService.saveGame(game);
        return getGame(principal);
    }

    @Override
    public SkockoGame getGame(Principal principal) {
        Game game=null;
        try {
            game=gameService.getGame(principal);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        if (game.getGames().getSkockoGame()!=null){
            return game.getGames().getSkockoGame();
        }
        else{
            SkockoGame skockoGame=createNewGame();
            game.getTimers(principal).setStartTimeSkocko(LocalTime.now());
            game.getGames().setSkockoGame(skockoGame);
            gameService.saveGame(game);
            return game.getGames().getSkockoGame();
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
    public ResponseEntity<SkockoResponse> handleSubmit(SkockoSubmit submit,Principal principal) throws GameNotFoundException {
        boolean isWinningCombination;
        SkockoResponse skockoResponse;
        Game game=gameService.getGame(principal);
        SkockoGame skockoGame=game.getGames().getSkockoGame();
        log.info("Processing submit for game: " + submit.getGameId() +". Submitted combination: " + submit.getCombination());

        isWinningCombination = isWinningCombination(skockoGame.getCombination(), submit.getCombination());

        if(isWinningCombination && ChronoUnit.SECONDS.between(game.getTimers(principal).getStartTimeSkocko(), LocalTime.now())<120){
            log.info("Returning object" + getResponseForWinningCombination(submit, game, principal) + "as response for submitting winning combination" + " for game id: " + skockoGame.getId());
            return ResponseEntity.ok()
                    .body( getResponseForWinningCombination(submit, game, principal));
        }
        skockoResponse = new SkockoResponseWithPositions(isWinningCombination, getNumberOfCorrectlyPlacedSymbolsInCombination(skockoGame.getCombination(), submit.getCombination()), getNumberOfMisplacedSymbolsInCombination(skockoGame.getCombination(), submit.getCombination()));
        log.info("Returning object" + skockoResponse + "as response for submitting non-winning combination" + " for game id: " + skockoGame.getId());
        return ResponseEntity.ok()
                .body(skockoResponse);
    }
    public SkockoResponse getResponseForWinningCombination(SkockoSubmit skockoSubmit, Game game, Principal principal) {
        int numOfPoints=numberOfPoints(skockoSubmit.getAttempt());
        if (game.getIsActive(principal).isActiveSkocko()){
            game.getPoints(principal).setNumOfPointsSkocko(numOfPoints);
            game.getIsActive(principal).setActiveSkocko(false);
            gameService.saveGame(game);
        }
        return new SkockoResponseWithNumberOfPoints(true, numOfPoints, game.getGames().getSkockoGame().getCombination());
    }
    @Override
    public boolean isWinningCombination(List<Integer> winningCombination, List<Integer> submittedCombination) {
        return submittedCombination.equals(winningCombination);
    }
    @Override
    public  int getNumberOfCorrectlyPlacedSymbolsInCombination(List<Integer> winningCombination, List<Integer> submittedCombination){
        int numberOfCorrectlyPlacedSymbols=0;
        for(int i=0; i<4; i++){
            if(submittedCombination.get(i).equals(winningCombination.get(i)))
                numberOfCorrectlyPlacedSymbols++;
        }
        return numberOfCorrectlyPlacedSymbols;
    }
    public int getNumberOfMisplacedSymbolsInCombination(List<Integer> winningCombination, List<Integer> submittedCombination){
        int numberOfMisplacedSymbols=0;
        int numberOfWrongPlacedSymbols=getNumberOfWrongPlacedSymbols(winningCombination, submittedCombination);
        for(int i=0; i<numberOfWrongPlacedSymbols; i++){
            for(int j=0; j<numberOfWrongPlacedSymbols; j++){
                if(winningCombination.get(i).equals(submittedCombination.get(j))){
                    numberOfMisplacedSymbols++;
                    break;
                }
            }
        }
        return numberOfMisplacedSymbols;
    }
    public int getNumberOfWrongPlacedSymbols(List<Integer> winningCombination, List<Integer> submittedCombination){
        int numberOfWrongPlacedSymbols=4;
        for(int i=0;i<numberOfWrongPlacedSymbols; i++){
            if(winningCombination.get(i).equals(submittedCombination.get(i))){
                winningCombination.remove(i);
                submittedCombination.remove(i);
                numberOfWrongPlacedSymbols--;
                i--;
            }
        }
        return numberOfWrongPlacedSymbols;
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
    public ResponseEntity<List<Integer>> getCombination(Principal principal) throws GameNotFoundException {
        Game game=gameService.getGame(principal);
        SkockoGame skockoGame=game.getGames().getSkockoGame();
        game.getIsActive(principal).setActiveSkocko(false);

        log.info("Returning combination for game id: " + skockoGame.getId());
        return ResponseEntity.ok().body(skockoGame.getCombination());

    }
    @Override
    public ResponseEntity<Response> finishGame(Principal principal) throws GameNotFoundException {
        Game game=gameService.getGame(principal);
        SkockoGame skockoGame=game.getGames().getSkockoGame();
        game.getIsActive(principal).setActiveSkocko(false);
        gameService.saveGame(game);
        skockoGameRepository.save(skockoGame);
        return ResponseEntity.ok().build();
    }
    @Override
    public boolean isActiveGame(Principal principal) throws GameNotFoundException {
        return gameService.getGame(principal).getIsActive(principal).isActiveSkocko();
    }
}
