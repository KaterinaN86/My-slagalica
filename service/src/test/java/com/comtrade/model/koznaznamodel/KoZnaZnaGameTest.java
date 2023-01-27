package com.comtrade.model.koznaznamodel;

import org.testng.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class KoZnaZnaGameTest {

    KoZnaZnaGame koZnaZnaGame;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        koZnaZnaGame = new KoZnaZnaGame();
    }

    @Test
    public void getId() {
        Long id = 1L;
        koZnaZnaGame.setId(1L);
        Assert.assertEquals(id, koZnaZnaGame.getId());
    }

    @Test
    public void getQuestions() {
        List<Question> questions = new ArrayList<>();
        Question q = new Question();
        questions.add(q);
        koZnaZnaGame.setQuestions(questions);
        Assert.assertEquals(questions, koZnaZnaGame.getQuestions());
    }


    @Test
    public void setId() {
        Long expected = 1L;
        try {
            Assert.assertNotEquals(expected, koZnaZnaGame.getId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        koZnaZnaGame.setId(1L);
        Long actual = 1L;
        expected = koZnaZnaGame.getId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setQuestions() {
        List<Question> questions = new ArrayList<>();
        Question q = new Question();
        questions.add(q);
        koZnaZnaGame.setQuestions(questions);
        try {
            Assert.assertNotEquals(questions, koZnaZnaGame.getQuestions());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        koZnaZnaGame.setId(1L);
        List<Question> actual = new ArrayList<>();
        questions = koZnaZnaGame.getQuestions();
        try {
            Assert.assertNotEquals(actual, questions);
        } catch (Exception e) {
            ec.addError(e);
        }
    }
}