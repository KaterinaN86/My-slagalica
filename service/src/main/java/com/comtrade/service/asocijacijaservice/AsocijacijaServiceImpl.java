package com.comtrade.service.asocijacijaservice;

import com.comtrade.model.games.OnePlayerGame;
import com.comtrade.model.Points;
import com.comtrade.model.asocijacijamodel.*;
import com.comtrade.repository.PointsRepository;
import com.comtrade.repository.TimersRepository;
import com.comtrade.repository.asocijacijarepository.AsocijacijaRepository;
import com.comtrade.repository.asocijacijarepository.WordRepository;
import com.comtrade.repository.gamerepository.OnePlayerGameRepository;
import com.comtrade.service.gameservice.OnePlayerOnePlayerGameServiceImpl;
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
    private OnePlayerOnePlayerGameServiceImpl gameService;

    private final OnePlayerGameRepository onePlayerGameRepository;
    public AsocijacijaServiceImpl(AsocijacijaRepository asocijacijaRepository, WordRepository wordRepository, PointsRepository pointsRepository, TimersRepository timersRepository, OnePlayerGameRepository onePlayerGameRepository) {
        this.asocijacijaRepository = asocijacijaRepository;
        this.wordRepository = wordRepository;
        this.pointsRepository = pointsRepository;
        this.timersRepository = timersRepository;
        this.onePlayerGameRepository = onePlayerGameRepository;
    }
    private WordModel getRandomWordModel() throws NoSuchElementException{
        int randomId = (int)(Math.floor((Math.random()*wordRepository.count()+1)));
        log.info("Searching for word model with id: " + randomId);
        Optional<WordModel> randomWordModel = wordRepository.findById(Long.valueOf(randomId));
        if(randomWordModel.isEmpty()){
            log.info("Word model with id: " + randomId + " does not exists");
            throw new NoSuchElementException();
            //System.out.println("No word model for id: " + randomId);
        }
        log.info("Returning word model with id: " + randomId);
        return randomWordModel.get();
    }

    //creating new Asocijacija game instance
    public ResponseEntity<Response> createNewAsocijacijaGame(Principal principal){
        try{
            OnePlayerGame game=gameService.getGame(principal);
            game.getIsActive().setActiveAsocijacije(true);
            log.info("Creating new Asocijacija game instance");
            AsocijacijaGame asocijacijaGame = new AsocijacijaGame();
            asocijacijaGame.setWordModel(getRandomWordModel());
            game.getTimers().setStartTimeAsocijacije(LocalTime.now());
            timersRepository.save(game.getTimers());
            asocijacijaRepository.save(asocijacijaGame);
            if (game.getGames().getAsocijacijaGame()==null){
                game.getGames().setAsocijacijaGame(asocijacijaGame);
                onePlayerGameRepository.save(game);
            }
            AsocijacijaGame savedAsocijacijaGame = game.getGames().getAsocijacijaGame();
            log.info("Asocijacija game instance with id: " + savedAsocijacijaGame.getId() + " created.");
            return ResponseEntity.ok()
                    .body(new ResponseWithGameId(savedAsocijacijaGame.getId()));

        }catch (NoSuchElementException ex){
            log.info("Asocijacija game create failed.");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //getting optional asocijacija game
    private AsocijacijaGame findSpecificGame(Long gameId) throws NoSuchElementException{
        log.info("Searching asocijacija game instance with id: " + gameId);
        Optional<AsocijacijaGame> optionalAsocijacijaGame = asocijacijaRepository.findById(gameId);
        if(optionalAsocijacijaGame.isEmpty()){
            //todo handling - done
            //todo logging - done
            log.info("Asocijacija game instance with id: " + gameId + " not found.");
            throw new NoSuchElementException();
            //System.out.println("No asocijacija game with such id: " + gameId);
            //return null;
        }
        log.info("Returning asocijacija game instance with id: " + gameId);
        return optionalAsocijacijaGame.get();
    }

    public ResponseEntity<Response> getValueOfSpecificField(String fieldName,Principal principal){
        try{
            OnePlayerGame onePlayerGame = gameService.getGame(principal);
            Points points=onePlayerGame.getPoints();
            if(ChronoUnit.SECONDS.between(onePlayerGame.getTimers().getStartTimeAsocijacije(), LocalTime.now())>=120){
                finishGame(principal);
            }
            AsocijacijaGame asocijacijaGame = onePlayerGame.getGames().getAsocijacijaGame();
            long gameId=asocijacijaGame.getId();
            boolean isGameActive = onePlayerGame.getIsActive().isActiveAsocijacije();
            String fieldNameUpperCase = fieldName.toUpperCase();

            if(isGameActive && (fieldName.contains("5") || fieldName.contains("final"))){
                //It does not return value of field A|B|C|D 5(column final word) or final word of a game if it is active
                log.info("Forbidden request for field:" + fieldName + " - game with id: " + gameId + " still active.");
                return ResponseEntity.status(403).build();
            }else if(!isGameActive){
                log.info("aaaaaReturning value of field: " + fieldName + " for game id: " + gameId);
                return ResponseEntity.ok()
                        .body(new ResponseWithFieldValue(findValueOfSpecificCell(findSpecificColumn(asocijacijaGame,fieldNameUpperCase),fieldNameUpperCase)));
            }
            else{
                log.info("Returning value of field: " + fieldName + " for game id: " + gameId);
                points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() - 0.25);
                //asocijacijaGame.setNumOfPoints(asocijacijaGame.getNumOfPoints() - 0.25);
                asocijacijaRepository.save(asocijacijaGame);
                return ResponseEntity.ok()
                        .body(new ResponseWithFieldValue(findValueOfSpecificCell(findSpecificColumn(asocijacijaGame,fieldNameUpperCase),fieldNameUpperCase)));
            }
        }catch (NoSuchElementException | IndexOutOfBoundsException ex){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.info("principal error");
            return ResponseEntity.notFound().build();
        }
    }

    private String findValueOfSpecificField(AsocijacijaGame asocijacijaGame, String fieldName){
        if(fieldName.contains("final")){
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
            OnePlayerGame onePlayerGame = gameService.getGame(principal);
            AsocijacijaGame asocijacijaGame = onePlayerGame.getGames().getAsocijacijaGame();
            Points points = onePlayerGame.getPoints();
            if(ChronoUnit.SECONDS.between(onePlayerGame.getTimers().getStartTimeAsocijacije(), LocalTime.now())>=120){
                finishGame(principal);
            }
            String submittedWordUpperCase = submittedWord.toUpperCase();
            String referenceWordUpperCase = findValueOfSpecificField(asocijacijaGame,fieldName).toUpperCase();
            log.info("Checking submit for game id: " + gameId);
            if(submittedWordUpperCase.equals(referenceWordUpperCase)){
                if(fieldName.contains("final")){
                    onePlayerGame.getIsActive().setActiveAsocijacije(false);
                    points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() + 14);
                    pointsRepository.save(points);
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

    public void change(Long gameId){
        OnePlayerGame game = null;
        AsocijacijaGame asocijacijaGame = findSpecificGame(gameId);
        game.getIsActive().setActiveAsocijacije(false);
        asocijacijaRepository.save(asocijacijaGame);
    }

    public ResponseEntity<Response> getNumberOfPoints(Long gameId, Principal principal){
        try{
            OnePlayerGame onePlayerGame = gameService.getGame(principal);
            AsocijacijaGame asocijacijaGame = onePlayerGame.getGames().getAsocijacijaGame();
            Points points = onePlayerGame.getPoints();
            if(points.getNumOfPointsAsocijacije()<0){
                points.setNumOfPointsAsocijacije(0);
            }
            else {
                points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() + 4);
            }
            asocijacijaRepository.save(asocijacijaGame);
            pointsRepository.save(points);
            if(!onePlayerGame.getIsActive().isActiveAsocijacije()){
                return ResponseEntity.ok()
                        .body(new ResponseWithNumberOfPoints(points.getNumOfPointsAsocijacije()));
            }else{
                log.info("Forbidden request for number of points:" + "- game with id: " + gameId+ " still active.");
                return ResponseEntity.status(403).build();
            }
        }catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Response> finishGame(Principal principal) throws Exception {
        OnePlayerGame game = gameService.getGame(principal);
        AsocijacijaGame asocijacijaGame=game.getGames().getAsocijacijaGame();
        Points points = game.getPoints();
        if(!game.getIsActive().isActiveAsocijacije()){
            return ResponseEntity.ok().build();
        }
        game.getIsActive().setActiveAsocijacije(false);
        if(points.getNumOfPointsAsocijacije()<=0){
            points.setNumOfPointsAsocijacije(0);
        }
        else {
            points.setNumOfPointsAsocijacije(points.getNumOfPointsAsocijacije() + 4);
            //asocijacijaGame.setNumOfPoints(asocijacijaGame.getNumOfPoints()+4);
        }
        log.info("Number of points for Asocijacije game: "+ points.getNumOfPointsAsocijacije());
        onePlayerGameRepository.save(game);
        pointsRepository.save(points);
        return ResponseEntity.ok().build();
    }
}
