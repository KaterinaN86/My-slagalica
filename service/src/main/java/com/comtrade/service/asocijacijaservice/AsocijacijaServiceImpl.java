package com.comtrade.service.asocijacijaservice;

import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.exceptions.NumberOfPointsOutOfRange;
import com.comtrade.model.Timers;
import com.comtrade.model.games.Game;
import com.comtrade.model.Points;
import com.comtrade.model.asocijacijamodel.*;
import com.comtrade.repository.PointsRepository;
import com.comtrade.repository.TimersRepository;
import com.comtrade.repository.asocijacijarepository.AsocijacijaRepository;
import com.comtrade.repository.asocijacijarepository.WordRepository;
import com.comtrade.service.gameservice.GameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class AsocijacijaServiceImpl {

    private final AsocijacijaRepository asocijacijaRepository;
    private final WordRepository wordRepository;
    private final PointsRepository pointsRepository;
    private final TimersRepository timersRepository;

    @Autowired
    private GameServiceImpl gameService;

    private static final String FINAL_FIELD = "final";

    public AsocijacijaServiceImpl(AsocijacijaRepository asocijacijaRepository, WordRepository wordRepository, PointsRepository pointsRepository, TimersRepository timersRepository) {
        this.asocijacijaRepository = asocijacijaRepository;
        this.wordRepository = wordRepository;
        this.pointsRepository = pointsRepository;
        this.timersRepository = timersRepository;
    }
    private WordModel getRandomWordModel() throws NoSuchElementException{
        int randomId = (int)(Math.floor((Math.random()*wordRepository.count()+1)));
        log.info("Searching for word model with id: " + randomId);
        Optional<WordModel> randomWordModel = wordRepository.findById(Long.valueOf(randomId));
        if(randomWordModel.isEmpty()){
            log.info("Word model with id: " + randomId + " does not exists");
            throw new NoSuchElementException();
        }
        log.info("Returning word model with id: " + randomId);
        return randomWordModel.get();
    }

    public ResponseEntity<Response> getInitData(Principal principal) throws GameNotFoundException {
        Game game = gameService.getGame(principal);
        gameService.saveGame(game);
        Timers timers = game.getTimers(principal);
        timers.setStartTimeAsocijacije(LocalTime.now());
        timersRepository.save(timers);
        game.setTimers(principal, timers);
        gameService.saveGame(game);
        return createNewAsocijacijaGame(principal);
    }

    public ResponseEntity<Response> createNewAsocijacijaGame(Principal principal){
        try{
            Game game=gameService.getGame(principal);
            game.getIsActive(principal).setActiveAsocijacije(true);
            log.info("Creating new Asocijacija game instance");
            AsocijacijaGame asocijacijaGame = new AsocijacijaGame();
            asocijacijaGame.setWordModel(getRandomWordModel());
            asocijacijaRepository.save(asocijacijaGame);
            if (game.getGames().getAsocijacijaGame()==null){
                game.getGames().setAsocijacijaGame(asocijacijaGame);
                gameService.saveGame(game);
            }
            AsocijacijaGame savedAsocijacijaGame = game.getGames().getAsocijacijaGame();
            log.info("Asocijacija game instance with id: " + savedAsocijacijaGame.getId() + " created.");
            return ResponseEntity.ok()
                    .body(new ResponseWithGameId(savedAsocijacijaGame.getId()));

        }catch (Exception e){
            log.info("Asocijacija game create failed.");
            return ResponseEntity.notFound().build();
        }
    }

    private AsocijacijaGame findSpecificGame(Long gameId) throws NoSuchElementException{
        log.info("Searching asocijacija game instance with id: " + gameId);
        Optional<AsocijacijaGame> optionalAsocijacijaGame = asocijacijaRepository.findById(gameId);
        if(optionalAsocijacijaGame.isEmpty()){
            log.info("Asocijacija game instance with id: " + gameId + " not found.");
            throw new NoSuchElementException();
        }
        log.info("Returning asocijacija game instance with id: " + gameId);
        return optionalAsocijacijaGame.get();
    }

    public ResponseEntity<Response> getValueOfSpecificField(String fieldName,Principal principal){
        try{
            Game game = gameService.getGame(principal);
            Points points=game.getPoints(principal);
            if(ChronoUnit.SECONDS.between(game.getTimers(principal).getStartTimeAsocijacije(), LocalTime.now())>=120){
                finishGame(principal);
            }
            AsocijacijaGame asocijacijaGame = game.getGames().getAsocijacijaGame();
            long gameId=asocijacijaGame.getId();
            boolean isGameActive = game.getIsActive(principal).isActiveAsocijacije();
            String fieldNameUpperCase = fieldName.toUpperCase();

            if(isGameActive && (fieldName.contains("5") || fieldName.contains(FINAL_FIELD))){
                //It does not return value of field A|B|C|D 5(column final word) or final word of a game if it is active
                log.info("Forbidden request for field:" + fieldName + " - game with id: " + gameId + " still active.");
                return ResponseEntity.status(403).build();
            }else if(!isGameActive){
                log.info("Returning value of field: " + fieldName + " for game id: " + gameId);
                return ResponseEntity.ok()
                        .body(new ResponseWithFieldValue(findValueOfSpecificField(asocijacijaGame,fieldNameUpperCase)));
            }
            else{
                log.info("Returning value of field: " + fieldName + " for game id: " + gameId);
                points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() - 0.25);
                pointsRepository.save(points);
                return ResponseEntity.ok()
                        .body(new ResponseWithFieldValue(findValueOfSpecificField(asocijacijaGame,fieldNameUpperCase)));
            }
        }catch (NoSuchElementException | IndexOutOfBoundsException ex){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.info("principal error");
            return ResponseEntity.notFound().build();
        }
    }

    private String findValueOfSpecificField(AsocijacijaGame asocijacijaGame, String fieldName){
        if(fieldName.contains(FINAL_FIELD.toUpperCase()) || fieldName.contains(FINAL_FIELD)){
            return findFinalWord(asocijacijaGame);
        }else{
            return findValueOfSpecificCell(findSpecificColumn(asocijacijaGame,fieldName),fieldName);
        }
    }

    private String findSpecificColumn(AsocijacijaGame asocijacijaGame, String fieldName){
        if(fieldName.contains("A")){
            return asocijacijaGame.getWordModel().getColumna();
        } else if (fieldName.contains("B")) {
            return asocijacijaGame.getWordModel().getColumnb();
        } else if (fieldName.contains("C")) {
            return asocijacijaGame.getWordModel().getColumnc();
        }else{
            return asocijacijaGame.getWordModel().getColumnd();
        }
    }
    
    private String findValueOfSpecificCell(String column, String fieldName) throws IndexOutOfBoundsException{
        List<String> columnWords = new ArrayList<>(Arrays.asList(column.split(",")));
        if(fieldName.contains("1")){
            return columnWords.get(0);
        }else if(fieldName.contains("2")){
            return columnWords.get(1);
        } else if (fieldName.contains("3")) {
            return columnWords.get(2);
        } else if (fieldName.contains("4")) {
            return columnWords.get(3);
        } else if (fieldName.contains("5")) {
            return columnWords.get(4);
        }else{
            log.info("Field index out of bounds: " + fieldName);
            throw new IndexOutOfBoundsException();
        }
    }

    private String findFinalWord(AsocijacijaGame asocijacijaGame){
        return asocijacijaGame.getWordModel().getFinalWord();
    }

    public ResponseEntity<Response> checkSubmittedWord(Long gameId, String fieldName, String submittedWord, Principal principal){
        try{
            Game game = gameService.getGame(principal);
            AsocijacijaGame asocijacijaGame = game.getGames().getAsocijacijaGame();
            Points points = game.getPoints(principal);
            if(ChronoUnit.SECONDS.between(game.getTimers(principal).getStartTimeAsocijacije(), LocalTime.now())>=120){
                finishGame(principal);
            }
            String submittedWordUpperCase = submittedWord.toUpperCase();
            String referenceWordUpperCase = findValueOfSpecificField(asocijacijaGame,fieldName).toUpperCase();
            log.info("Checking submit for game id: " + gameId);
            if(submittedWordUpperCase.equals(referenceWordUpperCase)){
                if(fieldName.contains(FINAL_FIELD)){
                    game.getIsActive(principal).setActiveAsocijacije(false);
                    setNumOfPointForFinalField(points.getNumOfPointsAsocijacije(),points);
                }else{
                    points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() + 5);
                    pointsRepository.save(points);
                }
                return ResponseEntity.ok()
                        .body(new ResponseWithBoolean(true));
            }else{
                return ResponseEntity.ok()
                        .body(new ResponseWithBoolean(false));
            }
        }catch (Exception ex){
            return ResponseEntity.notFound().build();
        }
    }

    public void setNumOfPointForFinalField(double currentNumberOfPoints, Points points) throws NumberOfPointsOutOfRange {
        if(currentNumberOfPoints < 0){
            points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() + 34);
        } else if (currentNumberOfPoints >= 0 && currentNumberOfPoints<5) {
            points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() + 29);
        } else if (currentNumberOfPoints >= 5 && currentNumberOfPoints<10) {
            points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() + 24);
        } else if (currentNumberOfPoints >= 10 && currentNumberOfPoints<15) {
            points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() + 19);
        }
        else  if (currentNumberOfPoints >= 15 && currentNumberOfPoints<20){
            points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() + 14);
        }
        else {
            throw new NumberOfPointsOutOfRange("The number of points is out of the allowed range!");
        }
        pointsRepository.save(points);
    }

    public void change(Long gameId, Principal principal) throws GameNotFoundException {
        Game game = gameService.getGame(principal);
        AsocijacijaGame asocijacijaGame = findSpecificGame(gameId);
        game.getIsActive(principal).setActiveAsocijacije(false);
        asocijacijaRepository.save(asocijacijaGame);
    }

    public ResponseEntity<Response> getNumberOfPoints(Long gameId, Principal principal) throws GameNotFoundException {
        try{
            Game game = gameService.getGame(principal);
            Points points = game.getPoints(principal);
            if(game.getIsActive(principal).isActiveAsocijacije()){
                log.info("Forbidden request for number of points:" + "- game with id: " + gameId+ " still active.");
                return ResponseEntity.status(403).build();
            }
            else {
                if (points.getNumOfPointsAsocijacije() < 0) {
                    points.setNumOfPointsAsocijacije(0);
                } else {
                    points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() + 4);
                }
                pointsRepository.save(points);
                return ResponseEntity.ok()
                        .body(new ResponseWithNumberOfPoints(points.getNumOfPointsAsocijacije()));
            }
        }catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new GameNotFoundException(e.getMessage());
        }
    }

    public ResponseEntity<Response> finishGame(Principal principal) throws Exception {
        Game game = gameService.getGame(principal);
        Points points = game.getPoints(principal);
        if(!game.getIsActive(principal).isActiveAsocijacije()){
            return ResponseEntity.ok().build();
        }
        game.getIsActive(principal).setActiveAsocijacije(false);
        log.info("Number of points for Asocijacije game: "+ points.getNumOfPointsAsocijacije());
        return this.getNumberOfPoints(game.getGames().getId(), principal);
    }

    public boolean isActiveGame(Principal principal) throws GameNotFoundException {
        return gameService.getGame(principal).getIsActive(principal).isActiveAsocijacije();
    }
}
