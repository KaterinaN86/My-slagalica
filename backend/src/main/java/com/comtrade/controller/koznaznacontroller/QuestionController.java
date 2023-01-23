package com.comtrade.controller.koznaznacontroller;


import com.comtrade.responses.Response;
import com.comtrade.service.koznaznaservice.QuestionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/koznazna/question")
public class QuestionController {
    private final QuestionServiceImpl questionService;

    public QuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/getOptions/{gameId}")
    @CrossOrigin
    public ResponseEntity<List<String>> getOptions(@PathVariable Long gameId){
        return questionService.getOptions(gameId);
    }

    @GetMapping("/getQuestionContent/{gameId}")
    @CrossOrigin
    public ResponseEntity<Response> getQuestionContent(@PathVariable Long gameId){
        return questionService.getContent(gameId);
    }

    @GetMapping("/{questionId}/getAnswer")
    public ResponseEntity<Response> getAnswer(@PathVariable Long questionId){
        return questionService.getAnswer(questionId);
    }

}
