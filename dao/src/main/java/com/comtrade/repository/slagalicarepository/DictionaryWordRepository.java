package com.comtrade.repository.slagalicarepository;

import com.comtrade.model.slagalicamodel.DictionaryWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DictionaryWordRepository extends JpaRepository<DictionaryWord, Long> {

    @Query(value = "SELECT * FROM dictionary_words WHERE word_from_dictionary=(:word)", nativeQuery = true)
    Optional<DictionaryWord> findByWord(@Param("word") String word);
}
