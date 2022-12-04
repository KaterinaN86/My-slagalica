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
        slagalica = new Slagalica(1L, "SAMBOIREMALA", "MALA");
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

        // All args constructor
        slagalica = null;
        Long id = 3L;
        String word = "MOEAIRTISABI";
        String computerWord = "TISA";
        slagalica = new Slagalica(id, word, computerWord);

        Assertions.assertEquals(id, slagalica.getId());
        Assertions.assertEquals(word, slagalica.getLettersForFindingTheWord());

        // No args constructor
        Slagalica slagalica2 = new Slagalica();
        assertEquals(null, slagalica2.getId());
        assertEquals(null, slagalica2.getLettersForFindingTheWord());
        assertEquals(null, slagalica2.getComputerLongestWord());




    }

    @Test
    void setComputerLongestWord() {

        String expectedWord = "MALA";
        String realWord = slagalica.getComputerLongestWord();

        Assertions.assertEquals(expectedWord, realWord);

        slagalica.setComputerLongestWord("BORE");
        expectedWord = "BORE";
        realWord = slagalica.getComputerLongestWord();

        Assertions.assertEquals(expectedWord, realWord);
    }

    @Test
    void testBuilder() {

        slagalica = null;
        slagalica = Slagalica.builder().id(2L).lettersForFindingTheWord("MAROPELSAIMAS").computerLongestWord("OPEL").build();

        assertNotNull(slagalica);
        assertEquals("OPEL", slagalica.getComputerLongestWord());
    }
}