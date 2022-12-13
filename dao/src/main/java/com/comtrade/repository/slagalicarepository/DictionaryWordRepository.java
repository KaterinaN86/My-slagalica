package com.comtrade.repository.slagalicarepository;

import com.comtrade.model.slagalicamodel.DictionaryWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryWordRepository extends JpaRepository<DictionaryWord, Long> {

    @Query(value = "SELECT COUNT(*) FROM dictionary_words WHERE word_from_dictionary=(:word) LIMIT 1", nativeQuery = true)
    Integer findAllByWord(@Param("word") String word);
}