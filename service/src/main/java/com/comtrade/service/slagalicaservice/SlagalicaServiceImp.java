package com.comtrade.service.slagalicaservice;


import com.comtrade.model.slagalicamodel.DictionaryWord;
import com.comtrade.model.slagalicamodel.Slagalica;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.repository.slagalicarepository.DictionaryWordRepository;
import com.comtrade.repository.slagalicarepository.SlagalicaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SlagalicaServiceImp implements SlagalicaService {

    private final SlagalicaRepository slagalicaRepository;
    private final DictionaryWordRepository dictionaryWordRepository;

    public SlagalicaServiceImp(SlagalicaRepository slagalicaRepository,
                               DictionaryWordRepository dictionaryWordRepository) {
        this.slagalicaRepository = slagalicaRepository;
        this.dictionaryWordRepository = dictionaryWordRepository;
    }

    @Override
    public Slagalica saveLetterForFindingWords() {
        Slagalica slagalicaGame = new Slagalica();
        slagalicaGame.setLettersForFindingTheWord(lettersForFindingTheWord());
        slagalicaGame.setComputerLongestWord(computersLongestWord(slagalicaGame.getLettersForFindingTheWord()));
        slagalicaRepository.save(slagalicaGame);
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
    public Integer userWordProcessing(SlagalicaUserWordSubmit slagalicaUserWordSubmit) {

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


        return finalResult;
    }


}
