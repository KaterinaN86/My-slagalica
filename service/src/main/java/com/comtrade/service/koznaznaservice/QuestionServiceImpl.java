package com.comtrade.service.koznaznaservice;

import com.comtrade.model.koznaznamodel.Question;
import com.comtrade.responses.AnswerResponse;
import com.comtrade.responses.QuestionContentResponse;
import com.comtrade.responses.Response;
import com.comtrade.repository.koznaznarepository.QuestionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService{
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void save(List<Question> questions) {
        questionRepository.saveAll(questions);
    }

    @Override
    public ResponseEntity<List<String>> getOptions(Long gameId) {
        Optional<Question> existingQuestion = questionRepository.findById(gameId);
        if (existingQuestion.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        else {
            return ResponseEntity.ok()
                    .body(existingQuestion.get().getOptions());
        }
    }

    @Override
    public ResponseEntity<Response> getContent(Long gameId){
        Optional<Question> existingQuestion = questionRepository.findById(gameId);
        if (existingQuestion.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        else {
            QuestionContentResponse questionContentResponse=new QuestionContentResponse();
            questionContentResponse.setQuestion(existingQuestion.get().getQuestionContent());
            return ResponseEntity.ok()
                    .body(questionContentResponse);
        }
    }

    @Override
    public ResponseEntity<Response> getAnswer(Long questionId){
        Optional<Question> existingQuestion = questionRepository.findById(questionId);
        if (existingQuestion.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        else {
            return ResponseEntity.ok()
                    .body(new AnswerResponse(existingQuestion.get().getCorrectAnswer()));
        }
    }

}
