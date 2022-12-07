package com.comtrade;


import com.comtrade.model.koznaznamodel.Question;
import com.comtrade.model.slagalicamodel.DictionaryWord;
import com.comtrade.repository.slagalicarepository.DictionaryWordRepository;
import com.comtrade.service.koznaznaservice.QuestionServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	@Autowired
	DictionaryWordRepository dictionaryWordRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
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

		/*for (String word : wordDictionary.subList(1,wordDictionary.size())) {
			dictionaryWordRepository.save(DictionaryWord.builder().wordFromDictionary(word).build());
		}*/
	}
	@Bean
	CommandLineRunner runner(QuestionServiceImpl questionService) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Question>> typeReference = new TypeReference<List<Question>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/questions.json");
			try {
				List<Question> questions = mapper.readValue(inputStream,typeReference);
				questionService.save(questions);
				System.out.println("Questions Saved!");
			} catch (IOException e){
				System.out.println("Unable to save question: " + e.getMessage());
			}
		};
	}
}
