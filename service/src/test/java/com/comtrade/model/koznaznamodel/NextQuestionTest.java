package com.comtrade.model.koznaznamodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;


@RunWith(SpringRunner.class)
public class NextQuestionTest {

    NextQuestion nextQuestion;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        nextQuestion = new NextQuestion();
    }

    @Test
    public void getGameId() {
        Long expectedId = 1L;
        nextQuestion.setGameId(1L);
        Assert.assertEquals( expectedId, nextQuestion.getGameId());
    }

    @Test
    public void setGameId() {
        Long expected = 1L;
        try {
            Assert.assertNotEquals(expected, nextQuestion.getGameId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        nextQuestion.setGameId(1L);
        Long actual = 1L;
        expected = nextQuestion.getGameId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void testToString() {
        String expect = "";
        Assert.assertNotEquals(expect, nextQuestion.toString());
    }
}