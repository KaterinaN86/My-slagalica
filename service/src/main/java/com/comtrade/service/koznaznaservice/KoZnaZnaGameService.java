package com.comtrade.service.koznaznaservice;

import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.model.koznaznamodel.KoZnaZnaGame;
import com.comtrade.model.koznaznamodel.NextQuestion;
import com.comtrade.model.koznaznamodel.Question;
import com.comtrade.responses.Response;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface KoZnaZnaGameService {
    KoZnaZnaGame getGame(Principal principal) throws GameNotFoundException;
    KoZnaZnaGame createNewGame(Principal principal) throws GameNotFoundException;
    List<Question> getRandomQuestions();
    Set<Integer> getRandomQuestionIds();
    Integer getCurrentQuestionIndex(Long id);
    List<Question> getListOfQuestionsById(Long id);
    KoZnaZnaGame getGame(Long id);
    ResponseEntity<Response> checkSubmitedQuestion(Long gameId, Integer questionIndex, Long questionId, Integer selectedQuestion,Principal principal) throws GameNotFoundException;
    Integer getNumberOfPoints(Principal principal) throws GameNotFoundException;
    ResponseEntity<Response> updateQuestionNumber(NextQuestion nextQuestion, Principal principal) throws GameNotFoundException;
    ResponseEntity<Response> finishGame( Principal principal) throws GameNotFoundException;

    boolean isActiveGame(Principal principal) throws GameNotFoundException;
    KoZnaZnaGame getInitData(Principal principal) throws GameNotFoundException;
}
