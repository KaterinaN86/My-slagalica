package com.comtrade.service.slagalicaservice;


import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.slagalicamodel.DictionaryWord;
import com.comtrade.model.slagalicamodel.SlagalicaGame;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.repository.gamerepository.Gamerepository;
import com.comtrade.repository.slagalicarepository.DictionaryWordRepository;
import com.comtrade.repository.slagalicarepository.SlagalicaRepository;
import com.comtrade.service.gameservice.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class SlagalicaServiceImp implements SlagalicaService {

    private final Gamerepository gamerepository;
    private final SlagalicaRepository slagalicaRepository;
    private final DictionaryWordRepository dictionaryWordRepository;

    @Autowired
    private GameServiceImpl gameService;

    public SlagalicaServiceImp(Gamerepository gamerepository, SlagalicaRepository slagalicaRepository,
                               DictionaryWordRepository dictionaryWordRepository) {
        this.gamerepository = gamerepository;
        this.slagalicaRepository = slagalicaRepository;
        this.dictionaryWordRepository = dictionaryWordRepository;
    }

    @Override
    public SlagalicaGame saveLetterForFindingWords(Principal principal) {
        OnePlayerGame game=null;
        try {
            game=gameService.getGame(principal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (game.getSlagalicaGame()!=null){
            return game.getSlagalicaGame();
        }
        SlagalicaGame slagalicaGame = new SlagalicaGame();
        slagalicaGame.setLettersForFindingTheWord(lettersForFindingTheWord());
        slagalicaGame.setIsActive(true);
        slagalicaGame.setComputerLongestWord(computersLongestWord(slagalicaGame.getLettersForFindingTheWord()));
        SlagalicaGame Sgame=slagalicaRepository.save(slagalicaGame);
        game.setSlagalicaGame(Sgame);
        gamerepository.save(game);
        return slagalicaGame;
    }

    @Override
    public String computersLongestWord(String lettersForWord) {

        List<DictionaryWord> wordsDictionary = dictionaryWordRepository.findAll();
        String longestWord = "";

        for (DictionaryWord dictionaryWord : wordsDictionary) {

            String word = dictionaryWord.getWordFromDictionary();

            if (word.length() < longestWord.length()) {
                continue;
            }
            if (word.length() > lettersForWord.length()) {
                continue;
            }

            int wordIndex = 0;
            int inputIndex = 0;

            while (inputIndex < lettersForWord.length() && wordIndex < word.length()) {
                if (word.charAt(wordIndex) == lettersForWord.charAt(inputIndex)) {
                    wordIndex++;
                }
                inputIndex++;
            }
            if (wordIndex >= word.length()) {
                longestWord = word;
            }
        }

        return longestWord;
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

            numOfLetters = s.length();

        }



        return s;
    }


    @Override
    public Integer userWordProcessing(SlagalicaUserWordSubmit slagalicaUserWordSubmit,Principal principal) {
        OnePlayerGame game=null;
        try {
            game=gameService.getGame(principal);
            if(!game.getSlagalicaGame().getIsActive()){
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        int finalResult = 0;
        String lettersForUserWord = slagalicaUserWordSubmit.getLettersForFindingTheWord();
        String chosenUserWord = slagalicaUserWordSubmit.getUserWord();

        int result = 0;

        Map<Character, Integer> lettersOfUserWordMap = new HashMap<>();

        for (char letter : chosenUserWord.toUpperCase().toCharArray()) {
            if (!lettersOfUserWordMap.containsKey(letter)) {
                lettersOfUserWordMap.put(letter, 0);
            }
            int currentCounter = lettersOfUserWordMap.get(letter);
            lettersOfUserWordMap.put(letter, currentCounter + 1);
        }

        int occurrences = 0;

        Map<Character, Integer> numberOfUsedCharacters = new HashMap<>(lettersOfUserWordMap);

        for (char availableChar : lettersForUserWord.toCharArray()) {
            if (numberOfUsedCharacters.containsKey(availableChar)) {
                int currentCounter = numberOfUsedCharacters.get(availableChar);
                currentCounter--;
                if (currentCounter == 0) {
                    numberOfUsedCharacters.remove(availableChar);
                } else {
                    numberOfUsedCharacters.put(availableChar, currentCounter);
                }
                if (numberOfUsedCharacters.isEmpty()) {
                    occurrences++;
                    numberOfUsedCharacters = new HashMap<>(lettersOfUserWordMap);
                }
            }
        }

        int computerWordlength = Optional.of(slagalicaRepository.findAll().get(0).getComputerLongestWord().length()).orElse(0);

        if(dictionaryWordRepository.findAllByWord(chosenUserWord.toLowerCase()) >= 1 && occurrences > 0) {
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
        game.getSlagalicaGame().setNumOfPoints(finalResult);
        game.setNumOfPoints(game.getNumOfPoints()+finalResult);
        game.getSlagalicaGame().setIsActive(false);
        gamerepository.save(game);


        return finalResult;
    }


}
