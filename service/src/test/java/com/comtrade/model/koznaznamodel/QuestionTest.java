package com.comtrade.model.koznaznamodel;

import org.testng.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


@RunWith(SpringRunner.class)
public class QuestionTest {

    Question question;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        question = new Question();
    }

    @Test
    public void getId() {
        Long id = 1L;
        question.setId(1L);
        Assert.assertEquals(id, question.getId());
    }

    @Test
    public void getQuestionContent() {
        String content = "";
        question.setQuestionContent("");
        Assert.assertEquals(content, question.getQuestionContent());

    }

    @Test
    public void getOptions() {
        List<String> options = new ArrayList<>(Arrays.asList("DUNAV", "TISA", "MORAVA", "RAJNA"));
        question.setOptions(Arrays.asList("DUNAV", "TISA", "MORAVA", "RAJNA"));
        Assert.assertEquals(options, question.getOptions());

    }

    @Test
    public void getGames() {
        List<KoZnaZnaGame> game = new ArrayList<>();
        question.setGames(game);
        Assert.assertNotNull(question);
    }

    @Test
    public void getCorrectAnswer() {
        Integer answerPoint = 3;
        question.setCorrectAnswer(3);
        Assert.assertEquals(answerPoint, question.getCorrectAnswer());
    }

    @Test
    public void setId() {
        Long expected = 1L;
        try {
            Assert.assertNotEquals(expected, question.getId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        question.setId(1L);
        Long actual = 1L;
        expected = question.getId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setQuestionContent() {
        String expression = "";
        try {
            Assert.assertNotEquals(expression, question.getQuestionContent());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        question.setQuestionContent("");
        String actual = "";
        expression = question.getQuestionContent();
        try {
            Assert.assertEquals(actual, expression);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void setOptions() {
        List<String> options = new ArrayList<>(Arrays.asList("DUNAV", "TISA", "MORAVA", "RAJNA"));
        try {
            Assert.assertNotEquals(options, question.getOptions());
        } catch (Exception e) {
            ec.addError(e);
        }
        question.setOptions(Arrays.asList("DUNAV", "TISA", "MORAVA", "RAJNA"));
        List<String> actual = new ArrayList<>(Arrays.asList("DUNAV", "TISA", "MORAVA", "RAJNA"));
        try {
            Assert.assertEquals(actual,question.getOptions());
        }catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setGames() {
        List<KoZnaZnaGame> game = new ArrayList<>();
        try {
            Assert.assertNotEquals(game, question.getGames());
        } catch (Exception e) {
            ec.addError(e);
        }
        question.setGames(game);
        List<KoZnaZnaGame> actual = new ArrayList<>();
        Assert.assertEquals(actual, question.getGames());
    }

    @Test
    public void setCorrectAnswer() {
        Integer correctAnswer = 3;
        try {
            Assert.assertNotEquals(correctAnswer, question.getCorrectAnswer());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        question.setCorrectAnswer(20);
        Integer actual = 5;
        correctAnswer = question.getCorrectAnswer();
        try {
            Assert.assertNotEquals(actual, correctAnswer);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void contructorTest() {
        Long id = 1L;
        String content = "KOJA REKA ČINI NAJVEĆI DEO GRANICE IZMEĐU BUGARSKE I RUMUNIJE?";
        List<String> options = new ArrayList<>(Arrays.asList("DUNAV", "TISA", "MORAVA", "RAJNA"));
        List<KoZnaZnaGame> game = new ArrayList<>();
        Integer correctAnswer = 1;

        question.setId(1L);
        question.setQuestionContent("KOJA REKA ČINI NAJVEĆI DEO GRANICE IZMEĐU BUGARSKE I RUMUNIJE?");
        question.setOptions(Arrays.asList("DUNAV", "TISA", "MORAVA", "RAJNA"));
        question.setGames(new ArrayList<>());
        question.setCorrectAnswer(1);

        Assert.assertEquals(id, question.getId());
        Assert.assertEquals(content, question.getQuestionContent());
        Assert.assertEquals(options, question.getOptions());
        Assert.assertEquals(game, question.getGames());
        Assert.assertEquals(correctAnswer, question.getCorrectAnswer());
    }
}