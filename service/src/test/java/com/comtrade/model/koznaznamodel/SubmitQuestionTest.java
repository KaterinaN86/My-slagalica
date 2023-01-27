package com.comtrade.model.koznaznamodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class SubmitQuestionTest {
    SubmitQuestion submitQuestion;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        submitQuestion = new SubmitQuestion();
    }

    @Test
    public void getGameId() {
        Long expectedId = 1L;
        submitQuestion.setGameId(1L);
        Assert.assertEquals( expectedId, submitQuestion.getGameId());

    }

    @Test
    public void getQuestionIndex() {
        Integer questionIndex = 3;
        submitQuestion.setQuestionIndex(3);
        Assert.assertEquals(questionIndex, submitQuestion.getQuestionIndex());
    }

    @Test
    public void getQuestionId() {
        Long questionId = 1L;
        submitQuestion.setQuestionId(1L);
        Assert.assertEquals(questionId, submitQuestion.getQuestionId());
    }

    @Test
    public void getSelectedQuestion() {
        Integer selectedQuestion = 9;
        submitQuestion.setSelectedQuestion(9);
        Assert.assertEquals(selectedQuestion, submitQuestion.getSelectedQuestion());
    }

    @Test
    public void setGameId() {
        Long expected = 1L;
        try {
            Assert.assertNotEquals(expected, submitQuestion.getGameId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        submitQuestion.setGameId(1L);
        Long actual = 1L;
        expected = submitQuestion.getGameId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setQuestionIndex() {
        Integer questionIndex = 8;
        try {
            Assert.assertNotEquals(questionIndex, submitQuestion.getQuestionIndex());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        submitQuestion.setQuestionIndex(8);
        Integer actual = 8;
        questionIndex = submitQuestion.getQuestionIndex();
        try {
            Assert.assertEquals(actual, questionIndex);
        } catch (Exception e) {
            ec.addError(e);
        }

    }

    @Test
    public void setQuestionId() {
        Long expected = 1L;
        try {
            Assert.assertNotEquals(expected, submitQuestion.getQuestionId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        submitQuestion.setQuestionId(1L);
        Long actual = 1L;
        expected = submitQuestion.getQuestionId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setSelectedQuestion() {
        Integer selectedQuestion = 6;
        try {
            Assert.assertNotEquals(selectedQuestion, submitQuestion.getSelectedQuestion());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        submitQuestion.setSelectedQuestion(6);
        Integer actual = 6;
        selectedQuestion = submitQuestion.getSelectedQuestion();
        try {
            Assert.assertEquals(actual, selectedQuestion);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void testToString() {
        String expected = "";
        Assert.assertNotEquals(expected, submitQuestion.toString());
    }
}