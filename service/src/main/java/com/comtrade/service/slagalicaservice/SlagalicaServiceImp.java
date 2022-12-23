package com.comtrade.service.slagalicaservice;


import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.slagalicamodel.*;
import com.comtrade.repository.gamerepository.OnePlayerGameRepository;
import com.comtrade.repository.slagalicarepository.DictionaryWordRepository;
import com.comtrade.repository.slagalicarepository.SlagalicaRepository;
import com.comtrade.service.gameservice.OnePlayerOnePlayerGameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SlagalicaServiceImp implements SlagalicaService {

    private final OnePlayerGameRepository onePlayerGameRepository;
    private final SlagalicaRepository slagalicaRepository;
    private final DictionaryWordRepository dictionaryWordRepository;

    @Autowired
    private OnePlayerOnePlayerGameServiceImpl gameService;

    public SlagalicaServiceImp(OnePlayerGameRepository onePlayerGameRepository, SlagalicaRepository slagalicaRepository,
                               DictionaryWordRepository dictionaryWordRepository) {
        this.onePlayerGameRepository = onePlayerGameRepository;
        this.slagalicaRepository = slagalicaRepository;
        this.dictionaryWordRepository = dictionaryWordRepository;
    }

    @Override
    public LettersResponse saveLetterForFindingWords(Principal principal) {
        OnePlayerGame game=null;
        try {
            game=gameService.getGame(principal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (game.getGames().getSlagalicaGame()!=null){
            return new LettersResponse(game.getGames().getSlagalicaGame().getLettersForFindingTheWord());
        }
        SlagalicaGame slagalicaGame = new SlagalicaGame();
        slagalicaGame.setLettersForFindingTheWord(lettersForFindingTheWord());
        slagalicaGame.setIsActive(true);
        slagalicaGame.setComputerLongestWord(computersLongestWord(slagalicaGame.getLettersForFindingTheWord()));
        SlagalicaGame Sgame=slagalicaRepository.save(slagalicaGame);
        game.getTimers().setStartTimeSlagalica(LocalTime.now());
        game.getGames().setSlagalicaGame(Sgame);
        onePlayerGameRepository.save(game);
        return new LettersResponse(slagalicaGame.getLettersForFindingTheWord());
    }

    @Override
    public String computersLongestWord(String lettersForWord) {

        List<DictionaryWord> wordsDictionary = dictionaryWordRepository.findAll();
        String longestWord = "";
        HashMap<String,Integer> lettersForWordMap=convertStringToHashMap(lettersForWord);
        HashMap<String, Integer> temp=new HashMap<>();
        wordsDictionary=wordsDictionary.stream().filter((word)-> word.getWordFromDictionary().length()<=lettersForWord.length()).sorted().collect(Collectors.toList());
       for (DictionaryWord dictionaryWord : wordsDictionary) {
            String word = dictionaryWord.getWordFromDictionary();
            if (word.length() < longestWord.length()) {
                continue;
            }

            temp.clear();
            temp.putAll(lettersForWordMap);
            int letterCount=0;
            String stringCharacter="";
            while(word.contains("nj") || word.contains("Nj")){
                if(temp.containsKey("Nj") && temp.get("Nj")>0){
                    temp.put("Nj",temp.get("Nj")-1);
                    letterCount++;
                    if(word.replace("nj","").equals(word)){
                        word=word.replace("Nj","");
                    }
                    else word=word.replaceFirst("nj","");
                }
                else break;
            }
            while(word.contains("lj") || word.contains("Lj")){
                if(temp.containsKey("Lj") && temp.get("Lj")>0){
                    temp.put("Lj",temp.get("Lj")-1);
                    letterCount++;
                    if(word.replace("lj","").equals(word)){
                        word=word.replace("Lj","");
                    }
                    else word=word.replaceFirst("lj","");
                }
                else break;
            }

            while(word.contains("dž") || word.contains("Dž")){
                if(temp.containsKey("Dž") && temp.get("Dž")>0){
                    temp.put("Dž",temp.get("Dž")-1);
                    letterCount++;
                    if(word.replace("dž","").equals(word)){
                        word=word.replace("Dž","");
                    }
                    else word=word.replaceFirst("dž","");
                }
                else break;
            }

            for (Character c:  word.toCharArray()) {
                //convert char to uppercase
                if(c >= 'a' && c <= 'z') {
                    c = (char) ((int)c - 32);
                }
                else if(c == 'đ'){
                    c='Đ';
                } else if (c=='š' || c=='ž') {
                    c = (char) ((int)c-16);
                } else if(c=='č') {
                    c='Č';
                } else if (c=='ć') {
                    c='Ć';
                }
                stringCharacter=c.toString();
                if (temp.containsKey(stringCharacter) && temp.get(stringCharacter)!=0){
                    temp.put(stringCharacter,temp.get(stringCharacter)-1);
                    letterCount++;
                }
                else {
                    letterCount=0;
                    break;
                }
            }
            if (letterCount > longestWord.length()) {
                longestWord = dictionaryWord.getWordFromDictionary();
            }
        }

        return longestWord;
    }

    public static HashMap<String,Integer> convertStringToHashMap(String letters){
        HashMap<String,Integer> tmpHashMap=new HashMap<>();
        while (letters.contains("Dž")){
            if(tmpHashMap.containsKey("Dž")){
                tmpHashMap.put("Dž",tmpHashMap.get("Dž")+1);
            }
            else{
                tmpHashMap.putIfAbsent("Dž",1);
            }
            letters=letters.replaceFirst("Dž","");
        }
        while (letters.contains("Nj")){
            if(tmpHashMap.containsKey("Nj")){
                tmpHashMap.put("Nj",tmpHashMap.get("Nj")+1);
            }
            else{
                tmpHashMap.putIfAbsent("Nj",1);
            }
            letters=letters.replaceFirst("Nj","");
        }
        while (letters.contains("Lj")){
            if(tmpHashMap.containsKey("Lj")){
                tmpHashMap.put("Lj",tmpHashMap.get("Lj")+1);
            }
            else{
                tmpHashMap.putIfAbsent("Lj",1);
            }
            letters=letters.replaceFirst("Lj","");
        }

        for (Character c:  letters.toCharArray()) {
            if(tmpHashMap.containsKey(c.toString())){
                tmpHashMap.put(c.toString(),tmpHashMap.get(c.toString())+1);
            }
            tmpHashMap.putIfAbsent(c.toString(),1);
        }
        return tmpHashMap;
    }

    @Override
    public String lettersForFindingTheWord() {
        Random random = new Random();
        String[] vowels = {"A", "E", "I", "O", "U"};
        String[] consonants = {"B", "C", "Č", "Ć", "D", "Dž", "Đ", "F", "G", "H", "J", "K", "L", "Lj", "M", "N", "Nj", "P", "R", "S", "Š", "T", "Z", "Ž", "V"};

        String s = "";
        int numOfLetters = 0, numOfVowels = 0, numofConsonants = 0;

        while (numOfLetters < 12) {

            if (numOfVowels != 6 && numofConsonants != 6) {
                s += (vowels[random.nextInt(vowels.length)] + consonants[random.nextInt(consonants.length)]);
                numOfVowels++;
                numofConsonants++;
            } else {
                s += consonants[random.nextInt(consonants.length)];
            }
            numOfLetters = numOfVowels+numofConsonants;
        }

        return s;
    }


    @Override
    public SubmitResponse userWordProcessing(SlagalicaUserWordSubmit slagalicaUserWordSubmit, Principal principal) throws Exception {
        OnePlayerGame game=gameService.getGame(principal);
        if(!game.getGames().getSlagalicaGame().getIsActive()){
            return new SubmitResponse("",0);
        }
        int finalResult = 0;
        String chosenUserWord = slagalicaUserWordSubmit.getUserWord();

        int result = 0;
        int computerWordlength = Optional.of(slagalicaRepository.findAll().get(0).getComputerLongestWord().length()).orElse(0);
        String modifiedChosenWord="";
        if(chosenUserWord.length()>0){
            modifiedChosenWord= chosenUserWord.charAt(0) +chosenUserWord.substring(1).toLowerCase();
        }
        if(dictionaryWordRepository.findAllByWord(chosenUserWord.toLowerCase()) >= 1 || dictionaryWordRepository.findAllByWord(modifiedChosenWord) >= 1) {
            if(computerWordlength == chosenUserWord.length()) {
                result = chosenUserWord.length()*2 + 3;
            } else if(computerWordlength < chosenUserWord.length()) {
                result = chosenUserWord.length()*2 + 6;
            } else {
                result = chosenUserWord.length()*2;
            }

        } else {
            result = 0;
        }

        finalResult = result;
        game.getPoints().setNumOfPointsSlagalica(finalResult);
        game.getGames().getSlagalicaGame().setIsActive(false);
        onePlayerGameRepository.save(game);


        return new SubmitResponse(game.getGames().getSlagalicaGame().getComputerLongestWord(),finalResult);
    }



}
