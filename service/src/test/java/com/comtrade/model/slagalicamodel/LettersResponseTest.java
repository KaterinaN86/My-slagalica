package com.comtrade.model.slagalicamodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.NoSuchElementException;


@RunWith(SpringRunner.class)
public class LettersResponseTest {

    LettersResponse lettersResponse;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        lettersResponse = new LettersResponse("SAMBOIREMALA");
    }

    @Test
    public void getLettersForFindingTheWord() {
        String letters = "SAMBOIREMALA";
        lettersResponse.setLettersForFindingTheWord("SAMBOIREMALA");
        Assert.assertEquals(letters, lettersResponse.getLettersForFindingTheWord());

    }

    @Test
    public void setLettersForFindingTheWord() {
        String letters = "SAMBOIREMALA";
        try {
            Assertions.assertEquals(letters, lettersResponse.getLettersForFindingTheWord());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }

        lettersResponse.setLettersForFindingTheWord("SAMBOIREMALA");
        String actual = "SAMBOIREMALA";
        try {
            Assertions.assertEquals(actual, lettersResponse.getLettersForFindingTheWord());
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void testToString() {
        String expected = "";
        Assertions.assertNotEquals(expected, lettersResponse.toString());
    }

    @Test
    public void contructorTest() {
        String lettersForWord = "SAMBOIREMALA";
        lettersResponse = new LettersResponse("SAMBOIREMALA");
        Assert.assertEquals(lettersForWord, lettersResponse.getLettersForFindingTheWord());
        Assert.assertNotNull(lettersResponse);

    }
}

