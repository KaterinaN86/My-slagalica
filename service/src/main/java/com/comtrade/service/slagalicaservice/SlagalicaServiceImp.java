package com.comtrade.service.slagalicaservice;


import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.model.Timers;
import com.comtrade.model.games.Game;
import com.comtrade.model.slagalicamodel.*;
import com.comtrade.repository.TimersRepository;
import com.comtrade.repository.slagalicarepository.DictionaryWordRepository;
import com.comtrade.repository.slagalicarepository.SlagalicaRepository;
import com.comtrade.responses.Response;
import com.comtrade.service.gameservice.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalTime;
import java.util.*;

@Service
public class SlagalicaServiceImp implements SlagalicaService {

    private final TimersRepository timersRepository;
    private final SlagalicaRepository slagalicaRepository;
    private final DictionaryWordRepository dictionaryWordRepository;

    @Autowired
    private GameServiceImpl gameService;

    public SlagalicaServiceImp(TimersRepository timersRepository, SlagalicaRepository slagalicaRepository,
                               DictionaryWordRepository dictionaryWordRepository) {
        this.timersRepository = timersRepository;
        this.slagalicaRepository = slagalicaRepository;
        this.dictionaryWordRepository = dictionaryWordRepository;
    }

    @Override
    public LettersResponse getInitData(Principal principal) throws GameNotFoundException {
        Game game = gameService.getGame(principal);
        gameService.saveGame(game);
        Timers timers = game.getTimers(principal);
        timers.setStartTimeSlagalica(LocalTime.now());
        timersRepository.save(timers);
        game.setTimers(principal, timers);
        gameService.saveGame(game);
        return saveLetterForFindingWords(principal);
    }

    @Override
    public LettersResponse saveLetterForFindingWords(Principal principal) throws GameNotFoundException {
        Game game=gameService.getGame(principal);
        if (game.getGames().getSlagalicaGame()!=null){
            return new LettersResponse(game.getGames().getSlagalicaGame().getLettersForFindingTheWord());
        } else {
            SlagalicaGame slagalicaGame = new SlagalicaGame();
            slagalicaGame.setLettersForFindingTheWord(lettersForFindingTheWord());
            game.getIsActive(principal).setActiveSlagalica(true);
            slagalicaGame.setComputerLongestWord(computersLongestWord(slagalicaGame.getLettersForFindingTheWord()));
            slagalicaRepository.save(slagalicaGame);
            game.getGames().setSlagalicaGame(slagalicaGame);
            gameService.saveGame(game);
            return new LettersResponse(slagalicaGame.getLettersForFindingTheWord());
        }
    }

    @Override
    public String computersLongestWord(String lettersForWord) {

        List<DictionaryWord> wordsDictionary = dictionaryWordRepository.findAll();
        String longestWord = "";
        Map<String,Integer> lettersForWordMap=convertStringToHashMap(lettersForWord);
        HashMap<String, Integer> tempLettersForWordMap=new HashMap<>();
        wordsDictionary=wordsDictionary.stream().filter(word-> word.getWordFromDictionary().length()<=lettersForWord.length()).sorted().toList();
       for (DictionaryWord dictionaryWord : wordsDictionary) {
            String word = dictionaryWord.getWordFromDictionary();
            if (word.length() < longestWord.length()) {
                continue;
            }
           tempLettersForWordMap.clear();
           tempLettersForWordMap.putAll(lettersForWordMap);
           int letterCount=0;
           word=searchLetterWithTwoCharsInWord("nj", "Nj", word, tempLettersForWordMap, letterCount);
           word=searchLetterWithTwoCharsInWord("lj","Lj", word, tempLettersForWordMap, letterCount);
           word=searchLetterWithTwoCharsInWord("dž","Dž", word, tempLettersForWordMap, letterCount);

           letterCount=getLetterCount(word, tempLettersForWordMap, letterCount);
           System.out.println(tempLettersForWordMap.toString());
           if (letterCount > longestWord.length()) {
                longestWord = dictionaryWord.getWordFromDictionary();
           }
        }
        return longestWord;
    }

    public static String searchLetterWithTwoCharsInWord (String lowerCaseLetter, String upperCaseLetter, String word, Map<String, Integer> lettersForWord, int letterCount){
        while(word.contains(lowerCaseLetter) || word.contains(upperCaseLetter)){
            if(lettersForWord.containsKey(upperCaseLetter) && lettersForWord.get(upperCaseLetter)>0){
                lettersForWord.put(upperCaseLetter,lettersForWord.get(upperCaseLetter)-1);
                letterCount++;
                if(word.replace(lowerCaseLetter,"").equals(word)){
                    word=word.replace(upperCaseLetter,"");
                }
                else word=word.replaceFirst(lowerCaseLetter,"");
            }
            else break;
        }
        return word;
    }

    public static int getLetterCount(String word, Map<String, Integer> tempLettersForWordMap, int letterCount){
        String stringCharacter="";
        for (Character c:  word.toCharArray()) {
            c=convertCharToUppercase(c);
            stringCharacter=c.toString();
            if (tempLettersForWordMap.containsKey(stringCharacter) && tempLettersForWordMap.get(stringCharacter)!=0){
                tempLettersForWordMap.put(stringCharacter,tempLettersForWordMap.get(stringCharacter)-1);
                letterCount++;
            }
            else {
                return 0;
            }
        }
        return letterCount;
    }

    public static Character convertCharToUppercase (Character character){
        if(character >= 'a' && character <= 'z') {
            character = (char) ((int)character - 32);
        }
        else if(character == 'đ'){
            character='Đ';
        } else if (character=='š' || character=='ž') {
            character = (char) ((int)character-16);
        } else if(character=='č') {
            character='Č';
        } else if (character=='ć') {
            character='Ć';
        }
        return character;
    }
    
    public static Map<String,Integer> convertStringToHashMap(String letters){
        HashMap<String,Integer> tmpHashMap=new HashMap<>();
        letters=convertLetterWithTwoChars("Dž", letters, tmpHashMap);
        letters=convertLetterWithTwoChars("Nj", letters, tmpHashMap);
        letters=convertLetterWithTwoChars("Lj", letters, tmpHashMap);
        for (Character c:  letters.toCharArray()) {
            if(tmpHashMap.containsKey(c.toString())){
                tmpHashMap.put(c.toString(),tmpHashMap.get(c.toString())+1);
            }
            tmpHashMap.putIfAbsent(c.toString(),1);
        }
        return tmpHashMap;
    }

    public static String convertLetterWithTwoChars(String letter, String letters, Map<String, Integer> lettersHashMap){
        while (letters.contains(letter)){
            if(lettersHashMap.containsKey(letter)){
                lettersHashMap.put(letter,lettersHashMap.get(letter)+1);
            }
            else{
                lettersHashMap.putIfAbsent(letter,1);
            }
            letters=letters.replaceFirst(letter,"");
        }
        return letters;
    }

    @Override
    public String lettersForFindingTheWord() {
        Random random = new Random();
        String[] vowels = {"A", "E", "I", "O", "U"};
        String[] consonants = {"B", "C", "Č", "Ć", "D", "Dž", "Đ", "F", "G", "H", "J", "K", "L", "Lj", "M", "N", "Nj", "P", "R", "S", "Š", "T", "Z", "Ž", "V"};

        StringBuilder chosenletters = new StringBuilder();
        int numOfLetters = 0;
        int numOfVowels = 0;
        int numofConsonants = 0;
        while (numOfLetters < 12) {
            if (numOfVowels != 6 && numofConsonants != 6) {
                chosenletters.append(vowels[random.nextInt(vowels.length)]);
                chosenletters.append(consonants[random.nextInt(consonants.length)]);
                numOfVowels++;
                numofConsonants++;
            } else {
                chosenletters.append(consonants[random.nextInt(consonants.length)]);
            }
            numOfLetters = numOfVowels+numofConsonants;
        }
        return chosenletters.toString();
    }


    @Override
    public SubmitResponse userWordProcessing(SlagalicaUserWordSubmit slagalicaUserWordSubmit, Principal principal) throws GameNotFoundException {
        Game game=gameService.getGame(principal);
        if(!game.getIsActive(principal).isActiveSlagalica()){
            return new SubmitResponse("",0);
        }
        int numberOfPoints = calculateNumberOfPoints(slagalicaUserWordSubmit);
        game.getPoints(principal).setNumOfPointsSlagalica(numberOfPoints);
        game.getIsActive(principal).setActiveSlagalica(false);
        gameService.saveGame(game);
        return new SubmitResponse(game.getGames().getSlagalicaGame().getComputerLongestWord(),numberOfPoints);
    }

    public int calculateNumberOfPoints(SlagalicaUserWordSubmit slagalicaUserWordSubmit){
        String chosenUserWord = slagalicaUserWordSubmit.getUserWord();
        int computerWordlength = Optional.of(slagalicaRepository.findAll().get(0).getComputerLongestWord().length()).orElse(0);
        String modifiedChosenWord= (chosenUserWord.length()>0) ?  chosenUserWord.charAt(0) +chosenUserWord.substring(1).toLowerCase() : "";
        int result = 0;
        if(dictionaryWordRepository.findAllByWord(chosenUserWord.toLowerCase()) >= 1 || dictionaryWordRepository.findAllByWord(modifiedChosenWord) >= 1) {
            if(computerWordlength == chosenUserWord.length()) {
                result = chosenUserWord.length()*2 + 3;
            } else if(computerWordlength < chosenUserWord.length()) {
                result = chosenUserWord.length()*2 + 6;
            } else {
                result = chosenUserWord.length()*2;
            }
        }
        return result;
    }

    @Override
    public ResponseEntity<Response> finishGame(Principal principal) throws GameNotFoundException {
        Game game=gameService.getGame(principal);
        SlagalicaGame slagalicaGame=game.getGames().getSlagalicaGame();
        game.getIsActive(principal).setActiveSlagalica(false);
        gameService.saveGame(game);
        slagalicaRepository.save(slagalicaGame);
        return ResponseEntity.ok().build();
    }
    @Override
    public boolean isActiveGame(Principal principal) throws GameNotFoundException {
        return gameService.getGame(principal).getIsActive(principal).isActiveSlagalica();
    }
}
