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

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    DictionaryWordRepository dictionaryWordRepository;

    @Autowired
    QuestionRepository questionRepository;

    public DataInitializer(DictionaryWordRepository dictionaryWordRepository, QuestionRepository questionRepository) {
        this.dictionaryWordRepository = dictionaryWordRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        saveDictionaryWordsForSlagalicaGame();
        saveQuestionsForKoZnaZnaGame();
    }

    public void saveDictionaryWordsForSlagalicaGame() throws IOException {
        if (dictionaryWordRepository.count() == 0) {
            Set<DictionaryWord> dictionaryWords = sortWords();
            dictionaryWordRepository.saveAll(dictionaryWords);
            log.info("Dictionary words saved");
        }
        else {
            log.info("Dictionary words were already in database");
        }
    }

    public Set<DictionaryWord> sortWords() throws IOException {
        TreeSet<DictionaryWord> dictionaryWords = new TreeSet<>();
        readWords().forEach(word -> dictionaryWords.add(new DictionaryWord(word)));
        return dictionaryWords;
    }
    public List<String> readWords() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/peterjcarroll/recnik-api/master/serbian-latin.txt");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()));
        List<String> words=reader.lines().toList();
        reader.close();
        return words;
    }
    public void saveQuestionsForKoZnaZnaGame(){
        if (questionRepository.count()==0) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Question>> typeReference = new TypeReference<>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/questions.json");
            try {
                List<Question> questions = mapper.readValue(inputStream, typeReference);
                questionRepository.saveAll(questions);
                log.info("Questions Saved!");
            } catch (IOException e) {
                log.info("Unable to save question: " + e.getMessage());
            }
        }
        else {
            log.info("Questions were already in database.");
        }
    }
}