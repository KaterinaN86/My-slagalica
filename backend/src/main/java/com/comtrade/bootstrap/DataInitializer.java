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
        if (dictionaryWordRepository.count() == 0) {
            List<DictionaryWord> dictionaryWords = new ArrayList<>();
            URL url = new URL("https://raw.githubusercontent.com/peterjcarroll/recnik-api/master/serbian-latin.txt");
            BufferedReader read = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            List<String> words = read.lines().toList();
            for (String word : words) {
                dictionaryWords.add(new DictionaryWord(word));
            }

            dictionaryWordRepository.saveAll(dictionaryWords);
            log.info("Dictionary words saved");
        }
        else {
            log.info("Dictionary words were already in database");
        }
        if (questionRepository.count()==0) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Question>> typeReference = new TypeReference<List<Question>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/questions.json");
            try {
                List<Question> questions = mapper.readValue(inputStream, typeReference);
                //questionService.save(questions);
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