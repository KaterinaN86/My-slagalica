package com.comtrade.controller.koznaznacontroller;

import com.comtrade.model.koznaznamodel.NextQuestion;
import com.comtrade.model.koznaznamodel.SubmitQuestion;
import com.comtrade.model.koznaznamodel.responses.Response;
import com.comtrade.model.koznaznamodel.responses.ResponseGameWithoutAnswers;
import com.comtrade.model.koznaznamodel.responses.ResponseQuestionsWithoutAnswer;
import com.comtrade.service.koznaznaservice.KoZnaZnaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/koZnaZna")
public class KoZnaZnaGameController {
    private final KoZnaZnaServiceImpl koZnaZnaService;

    public KoZnaZnaGameController(KoZnaZnaServiceImpl koZnaZnaService) {
        this.koZnaZnaService = koZnaZnaService;
    }
    @GetMapping("/play")
    @CrossOrigin
    public ResponseEntity<Response> getNewGame(Principal principal) throws Exception {
        return ResponseEntity.ok()
                .body(new ResponseGameWithoutAnswers(koZnaZnaService.getGame(principal)));
    }

    @GetMapping("/getCurrentQuestion/{gameId}")
    public Integer getCurrentQuestion(@PathVariable Long gameId){
        return koZnaZnaService.getCurrentQuestionIndex(gameId);
    }

    @GetMapping("/getQuestions/{gameId}")
    public ResponseEntity<Response> getQuestions(@PathVariable Long gameId){
        return ResponseEntity.ok()
                .body(new ResponseQuestionsWithoutAnswer(koZnaZnaService.getListOfQuestionsById(gameId)));
    }

    @PostMapping("/submitQuestion")
    public ResponseEntity<Response> submitQuestion(@RequestBody SubmitQuestion submitQuestion){
        return koZnaZnaService.checkSubmitedQuestion(submitQuestion.getGameId(), submitQuestion.getQuestionIndex(), submitQuestion.getQuestionId(), submitQuestion.getSelectedQuestion());
    }

    @GetMapping("/numberOfPoints/{gameId}")
    public Integer getNumberOfPoints(@PathVariable Long gameId){
        return koZnaZnaService.getNumberOfPoints(gameId);
    }

    @PutMapping("/nextQuestion")
    public ResponseEntity<Response> updateQuestionNumber(@RequestBody NextQuestion nextQuestion){
        return koZnaZnaService.updateQuestionNumber(nextQuestion);
    }
    @PutMapping("/finishGame")
    public ResponseEntity<Response> finishGame(Principal principal) throws Exception {
        return koZnaZnaService.finishGame(principal);
    }
}
