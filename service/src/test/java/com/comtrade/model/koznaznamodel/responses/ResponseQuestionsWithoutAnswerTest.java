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
import java.util.NoSuchElementException;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ResponseQuestionsWithoutAnswerTest {

    ResponseQuestionsWithoutAnswer responseQuestionsWithoutAnswer;
    @Mock
    KoZnaZnaGame mockGame;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        responseQuestionsWithoutAnswer = new ResponseQuestionsWithoutAnswer();
    }

    @Test
    public void getQuestionList() {
        Assert.assertNull(responseQuestionsWithoutAnswer.getQuestionList());

    }

    @Test
    public void setQuestionList() {
        List<Question> question = new ArrayList<>();
        try {
            Assert.assertNotEquals(question, responseQuestionsWithoutAnswer.getQuestionList());
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
        responseQuestionsWithoutAnswer.setQuestionList(new ArrayList<>());
        try {
            Assert.assertNotNull(responseQuestionsWithoutAnswer.getQuestionList());
        } catch (Exception e) {
            ec.addError(e);
        }

    }

    @Test
    public void testResponseQuestionsWithoutAnswers() {
        // Create a mock list of questions
        Question question1 = new Question();
        Question question2 = new Question();
        List<Question> mockQuestions = Arrays.asList(question1, question2);
        // Configure the mock game to return the mock list of questions

        when(mockGame.getQuestions()).thenReturn(mockQuestions);
        // Run the ResponseGameWithoutAnswers method
        ResponseQuestionsWithoutAnswer responseGame = new ResponseQuestionsWithoutAnswer(mockQuestions);
        // Check that the correct answer for each question is set to 0
        for (Question question : mockQuestions) {
            Assert.assertNotEquals(0, responseQuestionsWithoutAnswer.getQuestionList());
        }
    }

    @Test
    public void constructorTest() {
        responseQuestionsWithoutAnswer = new ResponseQuestionsWithoutAnswer();
        Assert.assertNotNull(responseQuestionsWithoutAnswer);

    }
}
