package com.comtrade.model.koznaznamodel.responses;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AnswerResponseTest {

    AnswerResponse answerResponse;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        answerResponse = new AnswerResponse(2);
    }

    @Test
    public void getAnswer() {
        Integer answer = 2;
        Assert.assertEquals(answer, answerResponse.getAnswer());
    }

    @Test
    public void setAnswer() {
        Integer answer = 2;
        try{
            Assert.assertEquals(answer, answerResponse.getAnswer());
        }catch (Exception e) {
            ec.addError(e);
        }
        answerResponse.setAnswer(2);
        Integer actual = 2;
        answer = answerResponse.getAnswer();
        try {
            Assert.assertEquals(actual, answer);
        } catch (Exception e) {
            ec.addError(e);
        }
    }
}