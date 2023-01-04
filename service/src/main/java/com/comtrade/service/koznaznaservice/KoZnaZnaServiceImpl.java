package com.comtrade.service.koznaznaservice;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.koznaznamodel.KoZnaZnaGame;
import com.comtrade.model.koznaznamodel.NextQuestion;
import com.comtrade.model.koznaznamodel.Question;
import com.comtrade.model.koznaznamodel.responses.AnswerResponse;
import com.comtrade.model.koznaznamodel.responses.Response;
import com.comtrade.repository.gamerepository.OnePlayerGameRepository;
import com.comtrade.repository.koznaznarepository.KoZnaZnaRepository;
import com.comtrade.repository.koznaznarepository.QuestionRepository;
import com.comtrade.service.gameservice.OnePlayerOnePlayerGameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class KoZnaZnaServiceImpl implements KoZnaZnaGameService{
        private final QuestionRepository questionRepository;
        private final KoZnaZnaRepository koZnaZnaRepository;
        private final OnePlayerGameRepository onePlayerGameRepository;
        @Autowired
        private OnePlayerOnePlayerGameServiceImpl gameService;

    public KoZnaZnaServiceImpl(QuestionRepository questionRepository, KoZnaZnaRepository koZnaZnaRepository, OnePlayerGameRepository onePlayerGameRepository) {
        this.questionRepository = questionRepository;
        this.koZnaZnaRepository = koZnaZnaRepository;
        this.onePlayerGameRepository = onePlayerGameRepository;
    }

    @Override
    public KoZnaZnaGame getGame(Principal principal) throws Exception {
        OnePlayerGame game=gameService.getGame(principal);
        if(game.getGames().getKoZnaZnaGame()!=null){
            return game.getGames().getKoZnaZnaGame();
        }else{
            KoZnaZnaGame koZnaZnaGame=this.createNewGame(principal);
            game.getGames().setKoZnaZnaGame(koZnaZnaGame);
            game.getTimers().setStartTimeKoZnaZna(LocalTime.now());
            onePlayerGameRepository.save(game);
            return koZnaZnaGame;
        }
    }

    @Override
    public KoZnaZnaGame createNewGame(Principal principal) throws Exception {
            KoZnaZnaGame koZnaZnaGame=new KoZnaZnaGame();
            OnePlayerGame game = gameService.getGame(principal);
            koZnaZnaGame.setQuestions(getRandomQuestions());
            game.getIsActive().setActiveKoZnaZna(true);
            koZnaZnaGame.setIndexOfTheCurrentQuestion(0);
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

        public ResponseEntity<Response> checkSubmitedQuestion(Long gameId, Integer questionIndex, Long questionId, Integer selectedQuestion,Principal principal) throws Exception {
            OnePlayerGame onePlayerGame = gameService.getGame(principal);
            KoZnaZnaGame koZnaZnaGame=this.getGame(gameId);
            long numberOfSeconds = ChronoUnit.SECONDS.between(onePlayerGame.getTimers().getStartTimeKoZnaZna(), LocalTime.now());
            if(numberOfSeconds>=120){
                finishGame(principal);
            }
            Optional<Question> existingQuestion = questionRepository.findById(questionId);
            if (existingQuestion.isEmpty()) {
                return ResponseEntity.notFound()
                        .build();
            }
            else if(!onePlayerGame.getIsActive().isActiveKoZnaZna() || koZnaZnaGame.getIndexOfTheCurrentQuestion()!=questionIndex){
                return ResponseEntity.status(404)
                        .body(new AnswerResponse(0));
            }
            else {
                if(selectedQuestion.equals(koZnaZnaGame.getQuestions().get(questionIndex).getCorrectAnswer())){
                    onePlayerGame.getPoints().setNumOfPointsKoZnaZna(onePlayerGame.getPoints().getNumOfPointsKoZnaZna()+3);
                    //koZnaZnaGame.setNumOfPoints(koZnaZnaGame.getNumOfPoints()+3);
                } else if (!selectedQuestion.equals(0)) {
                    onePlayerGame.getPoints().setNumOfPointsKoZnaZna(onePlayerGame.getPoints().getNumOfPointsKoZnaZna()-1);
                    //koZnaZnaGame.setNumOfPoints(koZnaZnaGame.getNumOfPoints()-1);
                }
                koZnaZnaRepository.save(koZnaZnaGame);
                log.info("The submit button is clicked and the correct answer is displayed!");
                return ResponseEntity.ok()
                        .body(new AnswerResponse(existingQuestion.get().getCorrectAnswer()));
            }
    }

    @Override
    public Integer getNumberOfPoints(Principal principal) throws Exception {
        OnePlayerGame onePlayerGame=gameService.getGame(principal);
        return onePlayerGame.getPoints().getNumOfPointsKoZnaZna();
    }

    @Override
    public ResponseEntity<Response> updateQuestionNumber(NextQuestion nextQuestion, Principal principal) throws Exception {
        OnePlayerGame game = gameService.getGame(principal);
        KoZnaZnaGame koZnaZnaGame=this.getGame(nextQuestion.getGameId());
        if (!game.getIsActive().isActiveKoZnaZna()) {
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
        OnePlayerGame game=gameService.getGame(principal);
        KoZnaZnaGame koZnaZnaGame=game.getGames().getKoZnaZnaGame();
        game.getIsActive().setActiveKoZnaZna(false);
        //game.setNumOfPoints(game.getNumOfPoints()+koZnaZnaGame.getNumOfPoints());
        onePlayerGameRepository.save(game);
        koZnaZnaRepository.save(koZnaZnaGame);
        return ResponseEntity.ok().build();
    }
}
