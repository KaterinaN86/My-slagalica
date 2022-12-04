package com.comtrade;


import com.comtrade.model.slagalicamodel.DictionaryWord;
import com.comtrade.repository.slagalicarepository.DictionaryWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;
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
}
