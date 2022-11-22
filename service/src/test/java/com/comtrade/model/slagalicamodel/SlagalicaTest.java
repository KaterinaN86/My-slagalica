package com.comtrade.model.slagalicamodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class SlagalicaTest {

    Slagalica slagalica;

    static final Logger log = Logger.getLogger(SlagalicaTest.class.toString());

    @BeforeAll
    public static void setup() {
        log.info("Test for class Slagalica has started executing.");
    }

    @BeforeEach
    void setUp() {
        slagalica = new Slagalica(1L, "SAMBOIREMALA");
    }

    @Test
    void testSetId() {

        Long expectedId = 1L;
        Long realId = slagalica.getId();

        Assertions.assertEquals(expectedId, realId);

        slagalica.setId(5L);
        expectedId = 5L;
        realId = slagalica.getId();

        Assertions.assertEquals(expectedId, realId);

    }

    @Test
    void testSetLettersForFindingTheWord() {

        String expectedWord = "SAMBOIREMALA";
        String realWord = slagalica.getLettersForFindingTheWord();

        Assertions.assertEquals(expectedWord, realWord);

        slagalica.setLettersForFindingTheWord("HIMBRIROSAVA");
        expectedWord = "HIMBRIROSAVA";
        realWord = slagalica.getLettersForFindingTheWord();

        Assertions.assertEquals(expectedWord, realWord);
    }

    @Test
    void testConstructors() {

        slagalica = null;
        Long id = 3L;
        String word = "MOEAIRTISABI";
        slagalica = new Slagalica(id, word);

        Assertions.assertEquals(id, slagalica.getId());
        Assertions.assertEquals(word, slagalica.getLettersForFindingTheWord());


    }
}