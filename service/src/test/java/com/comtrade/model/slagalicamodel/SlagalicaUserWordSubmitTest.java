package com.comtrade.model.slagalicamodel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class SlagalicaUserWordSubmitTest {

    SlagalicaUserWordSubmit slagalicaUserWordSubmit;


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

        Assertions.assertEquals(expectedGameId, realGameId);

        slagalicaUserWordSubmit.setGameId(5L);
        expectedGameId = 5L;
        realGameId = slagalicaUserWordSubmit.getGameId();

        Assertions.assertEquals(expectedGameId, realGameId);

    }

    @Test
    void testSetUserWord() {

        String expectedUserWord = "MOLERI";
        String realUserWord = slagalicaUserWordSubmit.getUserWord();

        Assertions.assertEquals(expectedUserWord, realUserWord);

        slagalicaUserWordSubmit.setUserWord("VENERA");
        expectedUserWord = "VENERA";
        realUserWord = slagalicaUserWordSubmit.getUserWord();

        Assertions.assertEquals(expectedUserWord, realUserWord);
    }

    @Test
    void testSetLettersForFindingTheWord() {

        String expectedLettersForUserWord = "SAMBOIREMALA";
        String realLettersForUserWord = slagalicaUserWordSubmit.getLettersForFindingTheWord();

        Assertions.assertEquals(expectedLettersForUserWord, realLettersForUserWord);

        slagalicaUserWordSubmit.setLettersForFindingTheWord("VENERA");
        expectedLettersForUserWord = "VENERA";
        realLettersForUserWord = slagalicaUserWordSubmit.getLettersForFindingTheWord();

        Assertions.assertEquals(expectedLettersForUserWord, realLettersForUserWord);
    }

    @Test
    void testConstructors() {

        slagalicaUserWordSubmit = null;
        Long gameId = 6L;
        String userWord = "SABIRATI";
        String lettersForUserWord = "MOEAIRTISABI";
        slagalicaUserWordSubmit = new SlagalicaUserWordSubmit(gameId, userWord, lettersForUserWord);

        Assertions.assertEquals(gameId, slagalicaUserWordSubmit.getGameId());
        Assertions.assertEquals(userWord, slagalicaUserWordSubmit.getUserWord());
        Assertions.assertEquals(lettersForUserWord, slagalicaUserWordSubmit.getLettersForFindingTheWord());

        SlagalicaUserWordSubmit slagalicaUserWordSubmit2 = new SlagalicaUserWordSubmit();
        assertEquals(null, slagalicaUserWordSubmit2.getGameId());
        assertEquals(null, slagalicaUserWordSubmit2.getLettersForFindingTheWord());
        assertEquals(null, slagalicaUserWordSubmit2.getUserWord());

    }

    @Test
    void testBuilder() {

        slagalicaUserWordSubmit = null;
        slagalicaUserWordSubmit = SlagalicaUserWordSubmit.builder().gameId(2L).lettersForFindingTheWord("MAROPELSAIMAS").userWord("OPEL").build();

        assertNotNull(slagalicaUserWordSubmit);
        assertEquals("OPEL", slagalicaUserWordSubmit.getUserWord());
    }
}