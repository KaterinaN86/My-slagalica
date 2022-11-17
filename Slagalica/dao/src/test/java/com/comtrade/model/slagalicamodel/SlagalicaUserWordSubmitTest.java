package com.comtrade.model.slagalicamodel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class SlagalicaUserWordSubmitTest {

    SlagalicaUserWordSubmit slagalicaUserWordSubmit;

    static final Logger log = Logger.getLogger(SlagalicaTest.class.toString());

    @BeforeAll
    public static void setup() {
        log.info("Test for class SlagalicaUserWordSubmit has started executing.");
    }

    @BeforeEach
    void setUp() {
        slagalicaUserWordSubmit = new SlagalicaUserWordSubmit(1L,"MOLERI", "SAMBOIREMALA");
    }

    @Test
    void testSetGameId() {

        Long expectedGameId = 1L;
        Long realGameId = slagalicaUserWordSubmit.getGameId();

        assertEquals(expectedGameId, realGameId);

        slagalicaUserWordSubmit.setGameId(5L);
        expectedGameId = 5L;
        realGameId = slagalicaUserWordSubmit.getGameId();

        assertEquals(expectedGameId, realGameId);

    }

    @Test
    void testSetUserWord() {

        String expectedUserWord = "MOLERI";
        String realUserWord = slagalicaUserWordSubmit.getUserWord();

        assertEquals(expectedUserWord, realUserWord);

        slagalicaUserWordSubmit.setUserWord("VENERA");
        expectedUserWord = "VENERA";
        realUserWord = slagalicaUserWordSubmit.getUserWord();

        assertEquals(expectedUserWord, realUserWord);
    }

    @Test
    void testSetLettersForFindingTheWord() {

        String expectedLettersForUserWord = "SAMBOIREMALA";
        String realLettersForUserWord = slagalicaUserWordSubmit.getLettersForFindingTheWord();

        assertEquals(expectedLettersForUserWord, realLettersForUserWord);

        slagalicaUserWordSubmit.setLettersForFindingTheWord("VENERA");
        expectedLettersForUserWord = "VENERA";
        realLettersForUserWord = slagalicaUserWordSubmit.getLettersForFindingTheWord();

        assertEquals(expectedLettersForUserWord, realLettersForUserWord);
    }

    @Test
    void testConstructors() {

        slagalicaUserWordSubmit = null;
        Long gameId = 6L;
        String userWord = "SABIRATI";
        String lettersForUserWord = "MOEAIRTISABI";
        slagalicaUserWordSubmit = new SlagalicaUserWordSubmit(gameId, userWord, lettersForUserWord);

        assertEquals(gameId, slagalicaUserWordSubmit.getGameId());
        assertEquals(userWord, slagalicaUserWordSubmit.getUserWord());
        assertEquals(lettersForUserWord, slagalicaUserWordSubmit.getLettersForFindingTheWord());

    }
}