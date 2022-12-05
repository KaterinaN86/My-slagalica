package com.comtrade.model.slagalicamodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryWordTest {

    DictionaryWord dictionaryWord;

    @BeforeEach
    void setUp() {
        dictionaryWord = new DictionaryWord(1L, "dobrodošli");
    }

    @Test
    void testSetId() {

        Long expectedId = 1L;
        Long realId = dictionaryWord.getId();

        Assertions.assertEquals(expectedId, realId);

        dictionaryWord.setId(5L);
        expectedId = 5L;
        realId = dictionaryWord.getId();

        Assertions.assertEquals(expectedId, realId);
    }

    @Test
    void testSetWordFromDictionary() {

        String expectedWord = "dobrodošli";
        String realWord = dictionaryWord.getWordFromDictionary();

        Assertions.assertEquals(expectedWord, realWord);

        dictionaryWord.setWordFromDictionary("slagalica");
        expectedWord = "slagalica";
        realWord = dictionaryWord.getWordFromDictionary();

        Assertions.assertEquals(expectedWord, realWord);
    }

    @Test
    void testConstructors() {

        // All args constructor
        dictionaryWord = null;
        Long id = 5L;
        String wordFromDictionary = "reka";
        dictionaryWord = new DictionaryWord(id, wordFromDictionary);

        Assertions.assertEquals(id, dictionaryWord.getId());
        Assertions.assertEquals(wordFromDictionary, dictionaryWord.getWordFromDictionary());

        // No args constructor
        DictionaryWord dictionaryWord2 = new DictionaryWord();
        assertEquals(null, dictionaryWord2.getId());
        assertEquals(null, dictionaryWord2.getWordFromDictionary());

    }

    @Test
    void testBuilder() {
        dictionaryWord = null;
        dictionaryWord = DictionaryWord.builder().id(5L).wordFromDictionary("asocijacija").build();

        assertNotNull(dictionaryWord);
        assertEquals("asocijacija", dictionaryWord.getWordFromDictionary());
    }
}