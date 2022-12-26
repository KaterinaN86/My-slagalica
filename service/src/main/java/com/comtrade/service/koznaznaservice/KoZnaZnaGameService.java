package com.comtrade.service.koznaznaservice;

import com.comtrade.model.koznaznamodel.KoZnaZnaGame;
import com.comtrade.model.koznaznamodel.NextQuestion;
import com.comtrade.model.koznaznamodel.Question;
import com.comtrade.model.koznaznamodel.responses.Response;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface KoZnaZnaGameService {
    KoZnaZnaGame getGame(Principal principal) throws Exception;
    KoZnaZnaGame createNewGame(Principal principal) throws Exception;
    List<Question> getRandomQuestions();
    Set<Integer> getRandomQuestionIds();
    Integer getCurrentQuestionIndex(Long id);
    List<Question> getListOfQuestionsById(Long id);
    KoZnaZnaGame getGame(Long id);
    ResponseEntity<Response> checkSubmitedQuestion(Long gameId, Integer questionIndex, Long questionId, Integer selectedQuestion,Principal principal) throws Exception;
    Integer getNumberOfPoints(Principal principal) throws Exception;
    ResponseEntity<Response> updateQuestionNumber(NextQuestion nextQuestion, Principal principal) throws Exception;
    ResponseEntity<Response> finishGame( Principal principal) throws Exception;
}
