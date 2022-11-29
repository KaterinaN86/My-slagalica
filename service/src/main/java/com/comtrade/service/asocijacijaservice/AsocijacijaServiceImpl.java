package com.comtrade.service.asocijacijaservice;

import com.comtrade.model.asocijacijamodel.*;
import com.comtrade.repository.asocijacijarepository.AsocijacijaRepository;
import com.comtrade.repository.asocijacijarepository.WordRepository;
import jakarta.persistence.Index;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class AsocijacijaServiceImpl {
    private final AsocijacijaRepository asocijacijaRepository;
    private final WordRepository wordRepository;

    public AsocijacijaServiceImpl(AsocijacijaRepository asocijacijaRepository, WordRepository wordRepository) {
        this.asocijacijaRepository = asocijacijaRepository;
        this.wordRepository = wordRepository;
    }

    private WordModel getRandomWordModel() throws NoSuchElementException{
        int randomId = (int)(Math.floor((Math.random()*wordRepository.count()+1)));
        Optional<WordModel> randomWordModel = wordRepository.findById(Long.valueOf(randomId));
        if(randomWordModel.isEmpty()){
            //todo handling - done
            //todo logging
            throw new NoSuchElementException();
            //System.out.println("No word model for id: " + randomId);
        }
        return randomWordModel.get();
    }

    //creating new Asocijacija game instance
    public ResponseEntity<Response> createNewAsocijacijaGame(){
        try{
            AsocijacijaModel asocijacijaGame = new AsocijacijaModel();
            asocijacijaGame.setWordModel(getRandomWordModel());
            AsocijacijaModel savedAsocijacijaGame = asocijacijaRepository.save(asocijacijaGame);

            return ResponseEntity.ok()
                    .body(new ResponseWithGameId(savedAsocijacijaGame.getId()));

        }catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        }
    }

    //getting optional asocijacija game
    private AsocijacijaModel findSpecificGame(Long gameId) throws NoSuchElementException{
        Optional<AsocijacijaModel> optionalAsocijacijaGame = asocijacijaRepository.findById(gameId);
        if(optionalAsocijacijaGame.isEmpty()){
            //todo handling
            //todo logging
            throw new NoSuchElementException();
            //System.out.println("No asocijacija game with such id: " + gameId);
            //return null;
        }
        return optionalAsocijacijaGame.get();
    }

    public ResponseEntity<Response> getValueOfSpecificField(Long gameId, String fieldName){
        try{
            AsocijacijaModel asocijacijaGame = findSpecificGame(gameId);
            boolean isGameActive = asocijacijaGame.isActive();
            String fieldNameUpperCase = fieldName.toUpperCase();

            if(isGameActive && (fieldName.contains("5") || fieldName.contains("final"))){
                //It does not return value of field A|B|C|D 5(column final word) or final word of a game if it is active
                return ResponseEntity.status(403).build();
            }else{
                return ResponseEntity.ok()
                        .body(new ResponseWithFieldValue(findValueOfSpecificCell(findSpecificColumn(asocijacijaGame,fieldNameUpperCase),fieldNameUpperCase)));
            }
        }catch (NoSuchElementException | IndexOutOfBoundsException ex){
            return ResponseEntity.notFound().build();
        }
    }

    private String findValueOfSpecificField(AsocijacijaModel asocijacijaGame, String fieldName){
        if(fieldName.contains("final")){
            return findFinalWord(asocijacijaGame);
        }else{
            return findValueOfSpecificCell(findSpecificColumn(asocijacijaGame,fieldName),fieldName);
        }
    }

    private String findSpecificColumn(AsocijacijaModel asocijacijaGame, String fieldName){
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
            //todo handling - done
            //todo logging
            throw new IndexOutOfBoundsException();
        }
    }

    private String findFinalWord(AsocijacijaModel asocijacijaGame){
        return asocijacijaGame.getWordModel().getFinalWord();
    }

    public ResponseEntity<Response> checkSubmittedWord(Long gameId, String fieldName, String submittedWord){
        try{
            AsocijacijaModel asocijacijaGame = findSpecificGame(gameId);
            String submittedWordUpperCase = submittedWord.toUpperCase();
            String referenceWordUpperCase = findValueOfSpecificField(asocijacijaGame,fieldName).toUpperCase();
            if(submittedWordUpperCase.equals(referenceWordUpperCase)){
                return ResponseEntity.ok()
                        .body(new ResponseWithBoolean(true));
            }else{
                return ResponseEntity.ok()
                        .body(new ResponseWithBoolean(false));
            }
        }catch (NoSuchElementException | IndexOutOfBoundsException ex){
            return ResponseEntity.notFound().build();
        }
    }

    public void change(Long gameId){
        AsocijacijaModel asocijacijaGame = findSpecificGame(gameId);
        asocijacijaGame.setActive(false);
        asocijacijaRepository.save(asocijacijaGame);
    }
}
