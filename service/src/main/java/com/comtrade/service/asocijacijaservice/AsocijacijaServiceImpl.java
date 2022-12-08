package com.comtrade.service.asocijacijaservice;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.asocijacijamodel.*;
import com.comtrade.repository.asocijacijarepository.AsocijacijaRepository;
import com.comtrade.repository.asocijacijarepository.WordRepository;
import com.comtrade.repository.gamerepository.Gamerepository;
import com.comtrade.service.gameservice.GameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.util.*;

@Service
@Slf4j
public class AsocijacijaServiceImpl {
    private final AsocijacijaRepository asocijacijaRepository;
    private final WordRepository wordRepository;

    @Autowired
    private GameServiceImpl gameService;

    private final Gamerepository gamerepository;
    public AsocijacijaServiceImpl(AsocijacijaRepository asocijacijaRepository, WordRepository wordRepository, Gamerepository gamerepository) {
        this.asocijacijaRepository = asocijacijaRepository;
        this.wordRepository = wordRepository;
        this.gamerepository = gamerepository;
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

            log.info("Creating new Asocijacija game instance");
            AsocijacijaGame asocijacijaGame = new AsocijacijaGame();
            asocijacijaGame.setWordModel(getRandomWordModel());
            asocijacijaRepository.save(asocijacijaGame);
            if (game.getAsocijacijaGame()==null){
                game.setAsocijacijaGame(asocijacijaGame);
                gamerepository.save(game);
            }
            AsocijacijaGame savedAsocijacijaGame = game.getAsocijacijaGame();
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

    public ResponseEntity<Response> getValueOfSpecificField(Long gameId, String fieldName){
        try{
            AsocijacijaGame asocijacijaGame = findSpecificGame(gameId);
            boolean isGameActive = asocijacijaGame.isActive();
            String fieldNameUpperCase = fieldName.toUpperCase();

            if(isGameActive && (fieldName.contains("5") || fieldName.contains("final"))){
                //It does not return value of field A|B|C|D 5(column final word) or final word of a game if it is active
                log.info("Forbidden request for field:" + fieldName + " - game with id: " + gameId + " still active.");
                return ResponseEntity.status(403).build();
            }else{
                log.info("Returning value of field: " + fieldName + " for game id: " + gameId);
                asocijacijaGame.setNumOfPoints(asocijacijaGame.getNumOfPoints() - 0.25);
                asocijacijaRepository.save(asocijacijaGame);
                return ResponseEntity.ok()
                        .body(new ResponseWithFieldValue(findValueOfSpecificCell(findSpecificColumn(asocijacijaGame,fieldNameUpperCase),fieldNameUpperCase)));
            }
        }catch (NoSuchElementException | IndexOutOfBoundsException ex){
            log.info("Returning value of field: " + fieldName + " for game id: " + gameId + " failed.");
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
            AsocijacijaGame asocijacijaGame = findSpecificGame(gameId);
            String submittedWordUpperCase = submittedWord.toUpperCase();
            String referenceWordUpperCase = findValueOfSpecificField(asocijacijaGame,fieldName).toUpperCase();
            log.info("Checking submit for game id: " + gameId);
            if(submittedWordUpperCase.equals(referenceWordUpperCase)){
                if(fieldName.contains("final")){
                    asocijacijaGame.setActive(false);
                    OnePlayerGame game=gameService.getGame(principal);
                    asocijacijaGame.setNumOfPoints(asocijacijaGame.getNumOfPoints() + 10);
                    game.setNumOfPoints((int) (game.getNumOfPoints()+asocijacijaGame.getNumOfPoints()));
                    asocijacijaRepository.save(asocijacijaGame);
                    gamerepository.save(game);
                }else{
                    asocijacijaGame.setNumOfPoints(asocijacijaGame.getNumOfPoints() + 5);
                    asocijacijaRepository.save(asocijacijaGame);
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
        AsocijacijaGame asocijacijaGame = findSpecificGame(gameId);
        asocijacijaGame.setActive(false);
        asocijacijaRepository.save(asocijacijaGame);
    }

    public ResponseEntity<Response> getNumberOfPoints(SubmitNumberOfFields submit){
        try{

            AsocijacijaGame asocijacijaGame = findSpecificGame(submit.getGameId());
            if(!asocijacijaGame.isActive()){

                return ResponseEntity.ok()
                        .body(new ResponseWithNumberOfPoints(asocijacijaGame.getNumOfPoints()));
            }else{
                log.info("Forbidden request for number of points:" + "- game with id: " + submit.getGameId() + " still active.");
                return ResponseEntity.status(403).build();
            }
        }catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
