package com.comtrade.bootstrap;


import com.comtrade.model.koznaznamodel.Question;
import com.comtrade.model.slagalicamodel.DictionaryWord;
import com.comtrade.repository.koznaznarepository.QuestionRepository;
import com.comtrade.repository.slagalicarepository.DictionaryWordRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    DictionaryWordRepository dictionaryWordRepository;

    @Autowired
    QuestionRepository questionRepository;

    public DataInitializer(DictionaryWordRepository dictionaryWordRepository) {
        this.dictionaryWordRepository = dictionaryWordRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        File file = ResourceUtils.getFile("classpath:static/serbian-latin.txt");
        List<String> wordDictionary = Files.readAllLines(file.toPath());
        List<DictionaryWord> dictionaryWords = new ArrayList<>();

        for(int i = 1; i < wordDictionary.size(); i++) {
            dictionaryWords.add(DictionaryWord.builder().wordFromDictionary(wordDictionary.get(i)).build());
        }

        dictionaryWordRepository.deleteAll();
        dictionaryWordRepository.flush();

        dictionaryWordRepository.saveAll(dictionaryWords);
        log.info("Dictionary words saved");

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Question>> typeReference = new TypeReference<List<Question>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/questions.json");
        try {
            List<Question> questions = mapper.readValue(inputStream,typeReference);
            //questionService.save(questions);
            questionRepository.saveAll(questions);
            log.info("Questions Saved!");
        } catch (IOException e){
            log.info("Unable to save question: " + e.getMessage());
        }
    }
}
