package com.comtrade.service.koznaznaservice;

import com.comtrade.model.koznaznamodel.Question;
import com.comtrade.responses.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {
    void save(List<Question> questions);
    ResponseEntity<List<String>> getOptions(Long gameId);
    ResponseEntity<Response> getContent(Long gameId);
    ResponseEntity<Response> getAnswer(Long questionId);
}
