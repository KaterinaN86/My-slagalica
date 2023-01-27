package com.comtrade.model.koznaznamodel.responses;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class QuestionContentResponseTest {

    QuestionContentResponse questionContentResponse;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        questionContentResponse = new QuestionContentResponse();
    }

    @Test
    public void getQuestion() {
        String question = "";
        Assert.assertNotEquals(question, questionContentResponse.getQuestion());

    }

    @Test
    public void setQuestion() {
        String question = "";
        try{
            Assert.assertNotEquals(question, questionContentResponse.getQuestion());
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
        questionContentResponse.setQuestion("");
        String actual = "";
        try{
            Assert.assertEquals(actual, questionContentResponse.getQuestion());
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void testToString() {
        String expect = "";
        Assert.assertNotEquals(expect, questionContentResponse.toString());
    }
}