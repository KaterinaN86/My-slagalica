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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@Slf4j
public class KoZnaZnaServiceImpl implements KoZnaZnaGameService{
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

    @Override
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

    @Override
    public KoZnaZnaGame createNewGame(){
            KoZnaZnaGame koZnaZnaGame=new KoZnaZnaGame();
            koZnaZnaGame.setQuestions(getRandomQuestions());
            koZnaZnaGame.setActiveGame(true);
            koZnaZnaGame.setIndexOfTheCurrentQuestion(0);
            koZnaZnaGame.setFinishedGame(false);
            koZnaZnaRepository.save(koZnaZnaGame);
            log.info("A new Ko zna zna game has been created!");
            return koZnaZnaGame;
        }
        @Override
        public List<Question> getRandomQuestions(){
            List<Question> questions=new ArrayList<>();
            for (long i: getRandomQuestionIds()) {
                questions.add(questionRepository.getReferenceById(i));
            }
            return questions;
        }
        @Override
        public Set<Integer> getRandomQuestionIds(){
            Set<Integer> ids=new HashSet<>();
            int numberOfQuestions=questionRepository.findAll().size();
            do{
                int random = (int)(Math.random()*numberOfQuestions+1);
                ids.add(random);
            }
            while (ids.size()!=10);
            return ids;
        }
        @Override
        public Integer getCurrentQuestionIndex(Long id){
            Optional<KoZnaZnaGame> existingGame=koZnaZnaRepository.findById(id);
            return existingGame.isPresent() ? existingGame.get().getIndexOfTheCurrentQuestion() : 0;
        }
        public List<Question> getListOfQuestionsById(Long id){
            Optional<KoZnaZnaGame> existingGame=koZnaZnaRepository.findById(id);
            return existingGame.isPresent() ? existingGame.get().getQuestions() : null;
        }
        @Override
        public KoZnaZnaGame getGame(Long id){
            Optional<KoZnaZnaGame> existingGame=koZnaZnaRepository.findById(id);
            return existingGame.isPresent() ? existingGame.get() : null;
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
                log.info("The submit button is clicked and the correct answer is displayed!");
                return ResponseEntity.ok()
                        .body(new AnswerResponse(existingQuestion.get().getCorrectAnswer()));
            }
    }

    @Override
    public Integer getNumberOfPoints(Long gameId) {
        KoZnaZnaGame koZnaZnaGame=this.getGame(gameId);
        return koZnaZnaGame.getNumOfPoints();
    }

    @Override
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

    @Override
    public ResponseEntity<Response> finishGame( Principal principal) throws Exception {
        OnePlayerGame game=null;
        game=gameService.getGame(principal);
        KoZnaZnaGame koZnaZnaGame=game.getKoZnaZnaGame();
        koZnaZnaGame.setFinishedGame(true);
        koZnaZnaGame.setActiveGame(false);
        game.setNumOfPoints(game.getNumOfPoints()+koZnaZnaGame.getNumOfPoints());
        gamerepository.save(game);
        koZnaZnaRepository.save(koZnaZnaGame);
        return ResponseEntity.ok().build();
    }
}
