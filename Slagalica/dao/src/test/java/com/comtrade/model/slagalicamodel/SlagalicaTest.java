package com.comtrade.model.slagalicamodel;

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

        assertEquals(expectedId, realId);

        slagalica.setId(5L);
        expectedId = 5L;
        realId = slagalica.getId();

        assertEquals(expectedId, realId);

    }

    @Test
    void testSetLettersForFindingTheWord() {

        String expectedWord = "SAMBOIREMALA";
        String realWord = slagalica.getLettersForFindingTheWord();

        assertEquals(expectedWord, realWord);

        slagalica.setLettersForFindingTheWord("HIMBRIROSAVA");
        expectedWord = "HIMBRIROSAVA";
        realWord = slagalica.getLettersForFindingTheWord();

        assertEquals(expectedWord, realWord);
    }

    @Test
    void testConstructors() {

        slagalica = null;
        Long id = 3L;
        String word = "MOEAIRTISABI";
        slagalica = new Slagalica(id, word);

        assertEquals(id, slagalica.getId());
        assertEquals(word, slagalica.getLettersForFindingTheWord());


    }
}