package com.comtrade.controller.koznaznacontroller;

import com.comtrade.model.koznaznamodel.SubmitQuestion;
import com.comtrade.model.koznaznamodel.responses.Response;
import com.comtrade.model.koznaznamodel.responses.ResponseGameWithoutAnswers;
import com.comtrade.model.koznaznamodel.responses.ResponseQuestionsWithoutAnswer;
import com.comtrade.repository.koznaznarepository.KoZnaZnaRepository;
import com.comtrade.service.koznaznaservice.KoZnaZnaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/koZnaZna")
public class KoZnaZnaGameController {
    private KoZnaZnaServiceImpl koZnaZnaService;

    public KoZnaZnaGameController(KoZnaZnaServiceImpl koZnaZnaService) {
        this.koZnaZnaService = koZnaZnaService;
    }
    @GetMapping("/play")
    @CrossOrigin
    public ResponseEntity<Response> getNewGame(){
        return ResponseEntity.ok()
                .body(new ResponseGameWithoutAnswers(koZnaZnaService.createNewGame()));
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

}
