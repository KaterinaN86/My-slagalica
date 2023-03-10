package com.comtrade.controller.koznaznacontroller;

import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.model.koznaznamodel.NextQuestion;
import com.comtrade.model.koznaznamodel.SubmitQuestion;
import com.comtrade.responses.Response;
import com.comtrade.responses.ResponseGameWithoutAnswers;
import com.comtrade.responses.ResponseQuestionsWithoutAnswer;
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
    public ResponseEntity<Response> getNewGame(Principal principal) throws GameNotFoundException {
        if(!koZnaZnaService.isActiveGame(principal)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .body(new ResponseGameWithoutAnswers(koZnaZnaService.getInitData(principal)));
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
    public ResponseEntity<Response> submitQuestion(@RequestBody SubmitQuestion submitQuestion, Principal principal) throws GameNotFoundException {
        return koZnaZnaService.checkSubmitedQuestion(submitQuestion.getGameId(), submitQuestion.getQuestionIndex(), submitQuestion.getQuestionId(), submitQuestion.getSelectedQuestion(),principal);
    }

    @GetMapping("/numberOfPoints")
    public Integer getNumberOfPoints(Principal principal) throws GameNotFoundException {
        return koZnaZnaService.getNumberOfPoints(principal);
    }

    @PutMapping("/nextQuestion")
    public ResponseEntity<Response> updateQuestionNumber(@RequestBody NextQuestion nextQuestion, Principal principal) throws GameNotFoundException {
        return koZnaZnaService.updateQuestionNumber(nextQuestion, principal);
    }
    @PutMapping("/finishGame")
    public ResponseEntity<Response> finishGame(Principal principal) throws GameNotFoundException {
        return koZnaZnaService.finishGame(principal);
    }
}
