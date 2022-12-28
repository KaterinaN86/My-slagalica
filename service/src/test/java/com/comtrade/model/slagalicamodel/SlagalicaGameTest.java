package com.comtrade.model.slagalicamodel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class SlagalicaGameTest {

    SlagalicaGame slagalicaGame;


    @BeforeAll
    public static void setup() {
        log.info("Test for class Slagalica has started executing.");
    }

    @BeforeEach
    void setUp() {
        slagalicaGame = new SlagalicaGame(1L, "SAMBOIREMALA", "MALA");
    }

    @Test
    void testSetId() {

        Long expectedId = 1L;
        Long realId = slagalicaGame.getId();

        Assertions.assertEquals(expectedId, realId);

        slagalicaGame.setId(5L);
        expectedId = 5L;
        realId = slagalicaGame.getId();

        Assertions.assertEquals(expectedId, realId);

    }

    @Test
    void testSetLettersForFindingTheWord() {

        String expectedWord = "SAMBOIREMALA";
        String realWord = slagalicaGame.getLettersForFindingTheWord();

        Assertions.assertEquals(expectedWord, realWord);

        slagalicaGame.setLettersForFindingTheWord("HIMBRIROSAVA");
        expectedWord = "HIMBRIROSAVA";
        realWord = slagalicaGame.getLettersForFindingTheWord();

        Assertions.assertEquals(expectedWord, realWord);
    }

    @Test
    void testConstructors() {

        // All args constructor
        slagalicaGame = null;
        Long id = 3L;
        String word = "MOEAIRTISABI";
        String computerWord = "TISA";
        slagalicaGame = new SlagalicaGame(id, word, computerWord);

        Assertions.assertEquals(id, slagalicaGame.getId());
        Assertions.assertEquals(word, slagalicaGame.getLettersForFindingTheWord());

        // No args constructor
        SlagalicaGame slagalicaGame2 = new SlagalicaGame();
        assertNull(slagalicaGame2.getId());
        assertNull(slagalicaGame2.getLettersForFindingTheWord());
        assertNull(slagalicaGame2.getComputerLongestWord());




    }

    @Test
    void setComputerLongestWord() {

        String expectedWord = "MALA";
        String realWord = slagalicaGame.getComputerLongestWord();

        Assertions.assertEquals(expectedWord, realWord);

        slagalicaGame.setComputerLongestWord("BORE");
        expectedWord = "BORE";
        realWord = slagalicaGame.getComputerLongestWord();

        Assertions.assertEquals(expectedWord, realWord);
    }

    @Test
    void testBuilder() {

        slagalicaGame = null;
        slagalicaGame = SlagalicaGame.builder().id(2L).lettersForFindingTheWord("MAROPELSAIMAS").computerLongestWord("OPEL").build();

        assertNotNull(slagalicaGame);
        assertEquals("OPEL", slagalicaGame.getComputerLongestWord());
    }
}