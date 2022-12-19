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

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        String currentPath = new java.io.File(".").getCanonicalPath();
        System.out.println("Current dir:" + currentPath);
        File file=null;
        List<DictionaryWord> dictionaryWords=new ArrayList<>();
        try {
            file=new File("serbian-latin.txt");//for docker just "serbian-latin.txt"
            Scanner reader = new Scanner(file);
            dictionaryWords = new ArrayList<>();

            while (reader.hasNextLine()) {
                DictionaryWord word=new DictionaryWord();
                word.setWordFromDictionary(reader.nextLine());
                dictionaryWords.add(word);
            }
            reader.close();
        }catch (FileNotFoundException e){
            file=new File("backend/src/main/resources/static/serbian-latin.txt");//for docker just "serbian-latin.txt"
            Scanner reader = new Scanner(file);
            dictionaryWords = new ArrayList<>();

            while (reader.hasNextLine()) {
                DictionaryWord word=new DictionaryWord();
                word.setWordFromDictionary(reader.nextLine());
                dictionaryWords.add(word);
            }
            System.out.println("nece da ucita");
        }


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
