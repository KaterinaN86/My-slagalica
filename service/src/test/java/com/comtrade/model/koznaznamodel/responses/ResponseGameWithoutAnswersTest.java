package com.comtrade.model.koznaznamodel.responses;

import com.comtrade.model.koznaznamodel.KoZnaZnaGame;
import com.comtrade.model.koznaznamodel.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ResponseGameWithoutAnswersTest {

    ResponseGameWithoutAnswers responseGameWithoutAnswers;

    @Mock
    KoZnaZnaGame mockGame;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        responseGameWithoutAnswers = new ResponseGameWithoutAnswers();
    }

    @Test
    public void getKoZnaZnaGame() {
        Assert.assertNotNull(responseGameWithoutAnswers);

    }

    @Test
    public void setKoZnaZnaGame() {
       KoZnaZnaGame game = new KoZnaZnaGame();
        try {
            Assert.assertNotEquals(game, responseGameWithoutAnswers.getKoZnaZnaGame());
        } catch (Exception e) {
            ec.addError(e);
        }

        responseGameWithoutAnswers.setKoZnaZnaGame(new KoZnaZnaGame());
        try {
            Assert.assertEquals(game, responseGameWithoutAnswers.getKoZnaZnaGame());
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void constructorTest() {
        List<Question> questions = new ArrayList<>();
        Assert.assertNotEquals(questions, responseGameWithoutAnswers.getKoZnaZnaGame());
    }

    @Test
    public void responseGameWithoutAnswer() {
        // Create a mock list of questions
        Question question1 = new Question();
        Question question2 = new Question();
        List<Question> mockQuestions = Arrays.asList(question1, question2);
        // Configure the mock game to return the mock list of questions

        when(mockGame.getQuestions()).thenReturn(mockQuestions);
        // Run the ResponseGameWithoutAnswers method
        ResponseGameWithoutAnswers responseGame = new ResponseGameWithoutAnswers(mockGame);
        // Check that the correct answer for each question is set to 0
        for (Question question : mockQuestions) {
                Assert.assertEquals(0, question.getCorrectAnswer());
            }
        }
}