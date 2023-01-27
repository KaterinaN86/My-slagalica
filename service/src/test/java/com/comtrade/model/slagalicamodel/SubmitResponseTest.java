package com.comtrade.model.slagalicamodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ErrorCollector;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;

@ExtendWith(SpringExtension.class)
class SubmitResponseTest {

    SubmitResponse submitResponse;

    private final ErrorCollector ec = new ErrorCollector();

    @BeforeEach
    void setUp() {
        submitResponse = new SubmitResponse("DORUČAK", 20);
    }

    @Test
    void getLongestWord() {
        String longestWord = "DORUČAK";
        submitResponse.setLongestWord("DORUČAK");
        Assertions.assertEquals(longestWord, submitResponse.getLongestWord());
    }

    @Test
    void getPoints() {
        Integer points = 20;
        submitResponse.setPoints(20);
        Assertions.assertEquals(points, submitResponse.getPoints());
    }

    @Test
    void setLongestWord() {
        String longestWord = "DOBRODOŠLICA";
        try {
            Assertions.assertNotEquals(longestWord, submitResponse.getLongestWord());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }

        submitResponse.setLongestWord("DOBRODOŠLICA");
        String actual = "DOBRODOŠLICA";
        try {
            Assertions.assertEquals(actual, submitResponse.getLongestWord());
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    void setPoints() {
        Integer points = null;
        try {
            Assertions.assertEquals(points, submitResponse.getPoints());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        submitResponse.setPoints(20);
        Integer actual = 20;
        points = submitResponse.getPoints();
        try {
            Assertions.assertEquals(actual, points);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    void testToString() {
        String expected = "";
        Assertions.assertNotEquals(expected, submitResponse.toString());
    }

    @Test
    public void contructorTest() {
        String longestWord = "DOBRODOŠLICA";
        Integer points = null;
        submitResponse = new SubmitResponse("DOBRODOŠLICA", null);
        Assertions.assertEquals(longestWord, submitResponse.getLongestWord());
        Assertions.assertEquals(points, submitResponse.getPoints());
        Assertions.assertNotNull(submitResponse);
    }
}