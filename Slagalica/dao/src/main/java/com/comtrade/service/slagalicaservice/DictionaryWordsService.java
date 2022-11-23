package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.DictionaryWord;
import com.comtrade.repository.slagalicarepository.DictionaryWordsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class DictionaryWordsService implements CommandLineRunner {

    private final DictionaryWordsRepository dictionaryWordsRepository;

    public DictionaryWordsService(DictionaryWordsRepository dictionaryWordsRepository) {
        this.dictionaryWordsRepository = dictionaryWordsRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        File file = ResourceUtils.getFile("classpath:static/serbian-latin.txt");
        List<String> wordDictionary = Files.readAllLines(file.toPath());

        for (String word : wordDictionary.subList(1,wordDictionary.size())) {
            dictionaryWordsRepository.save(DictionaryWord.builder().wordFromDictionary(word).build());
        }
    }

}
