package com.comtrade.service.koznaznaservice;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.koznaznamodel.KoZnaZnaGame;
import com.comtrade.model.koznaznamodel.NextQuestion;
import com.comtrade.model.koznaznamodel.Question;
import com.comtrade.model.koznaznamodel.responses.AnswerResponse;
import com.comtrade.model.koznaznamodel.responses.Response;
import com.comtrade.repository.gamerepository.Gamerepository;
import com.comtrade.repository.koznaznarepository.KoZnaZnaRepository;
import com.comtrade.repository.koznaznarepository.QuestionRepository;
import com.comtrade.service.gameservice.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class KoZnaZnaServiceImpl {
        private final QuestionRepository questionRepository;
        private final KoZnaZnaRepository koZnaZnaRepository;
        private final Gamerepository gamerepository;
        @Autowired
        private GameServiceImpl gameService;

    public KoZnaZnaServiceImpl(QuestionRepository questionRepository, KoZnaZnaRepository koZnaZnaRepository, Gamerepository gamerepository) {
        this.questionRepository = questionRepository;
        this.koZnaZnaRepository = koZnaZnaRepository;
        this.gamerepository = gamerepository;
    }

    public KoZnaZnaGame getGame(Principal principal) throws Exception {
        OnePlayerGame game=gameService.getGame(principal);
        if(game.getKoZnaZnaGame()!=null){
            return game.getKoZnaZnaGame();
        }else{
            KoZnaZnaGame koZnaZnaGame=this.createNewGame();
            game.setKoZnaZnaGame(koZnaZnaGame);
            gamerepository.save(game);
            return koZnaZnaGame;
        }
    }

    public KoZnaZnaGame createNewGame(){
            KoZnaZnaGame koZnaZnaGame=new KoZnaZnaGame((long) (koZnaZnaRepository.findAll().size()+1), getRandomQuestions(), 0, true, 0);
            koZnaZnaRepository.save(koZnaZnaGame);
            return koZnaZnaGame;
    }
        public List<Question> getRandomQuestions(){
            List<Question> questions=new ArrayList<>();
            for (long i: getRandomQuestionIds()) {
                if(questionRepository.getReferenceById(i)!=null){
                    questions.add(questionRepository.getReferenceById(i));
                }
            }
            return questions;
        }
        public Set<Integer> getRandomQuestionIds(){
            Set<Integer> ids=new TreeSet<>();
            int numberOfQuestions=questionRepository.findAll().size();
            do{
                int random = (int)(Math.random()*numberOfQuestions+1);
                ids.add(random);
            }
            while (ids.size()!=10);
            return ids;
        }
        public Integer getCurrentQuestionIndex(Long id){
            Optional<KoZnaZnaGame> existingGame=koZnaZnaRepository.findById(id);
            return  existingGame.get().getIndexOfTheCurrentQuestion();
        }
        public List<Question> getListOfQuestionsById(Long id){
            Optional<KoZnaZnaGame> existingGame=koZnaZnaRepository.findById(id);
            return  existingGame.get().getQuestions();
        }

        public KoZnaZnaGame getGame(Long id){
            Optional<KoZnaZnaGame> existingGame=koZnaZnaRepository.findById(id);
            return  existingGame.get();
        }

    public ResponseEntity<Response> checkSubmitedQuestion(Long gameId, Integer questionIndex, Long questionId, Integer selectedQuestion) {
        KoZnaZnaGame koZnaZnaGame=this.getGame(gameId);

        Optional<Question> existingQuestion = questionRepository.findById(questionId);
        if (existingQuestion.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        else if(!koZnaZnaGame.isActiveGame() || koZnaZnaGame.getIndexOfTheCurrentQuestion()!=questionIndex){
            return ResponseEntity.ok()
                    .body(new AnswerResponse(0));
        }
        else {
            if(selectedQuestion.equals(koZnaZnaGame.getQuestions().get(questionIndex).getCorrectAnswer())){
                koZnaZnaGame.setNumOfPoints(koZnaZnaGame.getNumOfPoints()+3);
            } else if (!selectedQuestion.equals(0)) {
                koZnaZnaGame.setNumOfPoints(koZnaZnaGame.getNumOfPoints()-1);
            }
            koZnaZnaRepository.save(koZnaZnaGame);
            return ResponseEntity.ok()
                    .body(new AnswerResponse(existingQuestion.get().getCorrectAnswer()));
        }
    }

    public Integer getNumberOfPoints(Long gameId) {
        KoZnaZnaGame koZnaZnaGame=this.getGame(gameId);
        koZnaZnaGame.setActiveGame(false);
        koZnaZnaRepository.save(koZnaZnaGame);
        return koZnaZnaGame.getNumOfPoints();
    }

    public ResponseEntity<Response> updateQuestionNumber(NextQuestion nextQuestion) {
        KoZnaZnaGame koZnaZnaGame=this.getGame(nextQuestion.getGameId());
        if (!koZnaZnaGame.isActiveGame()) {
            return ResponseEntity.notFound()
                    .build();
        }
        else {
            koZnaZnaGame.setIndexOfTheCurrentQuestion(koZnaZnaGame.getIndexOfTheCurrentQuestion()+1);
            koZnaZnaRepository.save(koZnaZnaGame);
            return ResponseEntity.ok().build();
        }

    }
}
