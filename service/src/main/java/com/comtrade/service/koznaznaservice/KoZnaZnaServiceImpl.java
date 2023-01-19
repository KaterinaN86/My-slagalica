package com.comtrade.service.koznaznaservice;

import com.comtrade.model.Timers;
import com.comtrade.model.games.Game;
import com.comtrade.model.games.OnePlayerGame;
import com.comtrade.model.games.TwoPlayerGame;
import com.comtrade.model.koznaznamodel.KoZnaZnaGame;
import com.comtrade.model.koznaznamodel.NextQuestion;
import com.comtrade.model.koznaznamodel.Question;
import com.comtrade.model.koznaznamodel.responses.AnswerResponse;
import com.comtrade.model.koznaznamodel.responses.Response;
import com.comtrade.repository.TimersRepository;
import com.comtrade.repository.koznaznarepository.KoZnaZnaRepository;
import com.comtrade.repository.koznaznarepository.QuestionRepository;
import com.comtrade.service.gameservice.GameServiceImpl;
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
        private final TimersRepository timersRepository;

        @Autowired
        private GameServiceImpl gameService;

    public KoZnaZnaServiceImpl(QuestionRepository questionRepository, KoZnaZnaRepository koZnaZnaRepository, TimersRepository timersRepository) {
        this.questionRepository = questionRepository;
        this.koZnaZnaRepository = koZnaZnaRepository;
        this.timersRepository = timersRepository;
    }

    @Override
    public KoZnaZnaGame getInitData(Principal principal) throws Exception {
        Game game = gameService.getGame(principal);
        gameService.saveGame(game);
        Timers timers = game.getTimers(principal);
        timers.setStartTimeKoZnaZna(LocalTime.now());
        timersRepository.save(timers);
        game.setTimers(principal, timers);
        gameService.saveGame(game);
        return getGame(principal);
    }

    @Override
    public KoZnaZnaGame getGame(Principal principal) throws Exception {
        Game game=gameService.getGame(principal);
        if(game.getGames().getKoZnaZnaGame()!=null){
            return game.getGames().getKoZnaZnaGame();
        }else{
            KoZnaZnaGame koZnaZnaGame=this.createNewGame(principal);
            game.getGames().setKoZnaZnaGame(koZnaZnaGame);
            gameService.saveGame(game);
            return koZnaZnaGame;
        }
    }

    @Override
    public KoZnaZnaGame createNewGame(Principal principal) throws Exception {
            KoZnaZnaGame koZnaZnaGame=new KoZnaZnaGame();
            Game game = gameService.getGame(principal);
            koZnaZnaGame.setQuestions(getRandomQuestions());
            game.getIsActive(principal).setActiveKoZnaZna(true);
            koZnaZnaGame.setIndexOfTheCurrentQuestionPlayerOne(0);
            koZnaZnaGame.setIndexOfTheCurrentQuestionPlayerTwo(0);
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
            return existingGame.isPresent() ? existingGame.get().getIndexOfTheCurrentQuestionPlayerOne() : 0;
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
            Game game = gameService.getGame(principal);
            KoZnaZnaGame koZnaZnaGame=this.getGame(gameId);
            long numberOfSeconds = ChronoUnit.SECONDS.between(game.getTimers(principal).getStartTimeKoZnaZna(), LocalTime.now());
            if(numberOfSeconds>=120){
                finishGame(principal);
            }
            Integer questionIndexForCurrentPlayer=getQuestionIndexForCurrentPlayer(game, koZnaZnaGame, principal);
            Optional<Question> existingQuestion = questionRepository.findById(questionId);
            if (existingQuestion.isEmpty()) {
                return ResponseEntity.notFound()
                        .build();
            }
            else if(!game.getIsActive(principal).isActiveKoZnaZna() || !questionIndexForCurrentPlayer.equals(questionIndex)){
                return ResponseEntity.status(404)
                        .body(new AnswerResponse(0));
            }
            else {
                if(selectedQuestion.equals(koZnaZnaGame.getQuestions().get(questionIndex).getCorrectAnswer())){
                    game.getPoints(principal).setNumOfPointsKoZnaZna(game.getPoints(principal).getNumOfPointsKoZnaZna()+3);
                } else if (!selectedQuestion.equals(0)) {
                    game.getPoints(principal).setNumOfPointsKoZnaZna(game.getPoints(principal).getNumOfPointsKoZnaZna()-1);
                }
                koZnaZnaRepository.save(koZnaZnaGame);
                log.info(principal.getName()+" is submited answer for "+(questionIndex+1)+". question!");
                return ResponseEntity.ok()
                        .body(new AnswerResponse(existingQuestion.get().getCorrectAnswer()));
            }
    }

    public static Integer getQuestionIndexForCurrentPlayer (Game game, KoZnaZnaGame koZnaZnaGame, Principal principal){
        int questionIndexForCurrentPlayer=0;
        if(game instanceof OnePlayerGame){
            questionIndexForCurrentPlayer=koZnaZnaGame.getIndexOfTheCurrentQuestionPlayerOne();
        }
        else if(game instanceof TwoPlayerGame twoPlayerGame && twoPlayerGame.getUser1().getUserName().equals(principal.getName())){
            questionIndexForCurrentPlayer=koZnaZnaGame.getIndexOfTheCurrentQuestionPlayerOne();
        }
        else if(game instanceof TwoPlayerGame twoPlayerGame && twoPlayerGame.getUser2().getUserName().equals(principal.getName())){
            questionIndexForCurrentPlayer=koZnaZnaGame.getIndexOfTheCurrentQuestionPlayerTwo();
        }
        return questionIndexForCurrentPlayer;
    }

    @Override
    public Integer getNumberOfPoints(Principal principal) throws Exception {
        Game game=gameService.getGame(principal);
        return game.getPoints(principal).getNumOfPointsKoZnaZna();
    }

    @Override
    public ResponseEntity<Response> updateQuestionNumber(NextQuestion nextQuestion, Principal principal) throws Exception {
        Game game = gameService.getGame(principal);
        KoZnaZnaGame koZnaZnaGame=this.getGame(nextQuestion.getGameId());
        if (!game.getIsActive(principal).isActiveKoZnaZna()) {
            return ResponseEntity.notFound()
                    .build();
        }
        else {
            if(game instanceof OnePlayerGame){
                updateQuestionNumberForOnePlayerGame(koZnaZnaGame);
            }
            else if(game instanceof TwoPlayerGame){
                updateQuestionNumberForTwoPlayerGame(principal, game);
            }
            gameService.saveGame(game);
            koZnaZnaRepository.save(koZnaZnaGame);
            return ResponseEntity.ok().build();
        }
    }

    @Override
    public ResponseEntity<Response> finishGame( Principal principal) throws Exception {
        Game game=gameService.getGame(principal);
        KoZnaZnaGame koZnaZnaGame=game.getGames().getKoZnaZnaGame();
        game.getIsActive(principal).setActiveKoZnaZna(false);
        gameService.saveGame(game);
        koZnaZnaRepository.save(koZnaZnaGame);
        return ResponseEntity.ok().build();
    }

    @Override
    public boolean isActiveGame(Principal principal) throws Exception {
        return gameService.getGame(principal).getIsActive(principal).isActiveKoZnaZna();
    }
    public void updateQuestionNumberForOnePlayerGame (KoZnaZnaGame koZnaZnaGame){
        koZnaZnaGame.setIndexOfTheCurrentQuestionPlayerOne(koZnaZnaGame.getIndexOfTheCurrentQuestionPlayerOne()+1);
    }
    public void updateQuestionNumberForTwoPlayerGame (Principal principal, Game game){
        TwoPlayerGame twoPlayerGame=(TwoPlayerGame) game;
        KoZnaZnaGame koZnaZnaGame=game.getGames().getKoZnaZnaGame();
        if(twoPlayerGame.getUser1().getUserName().equals(principal.getName())){
            koZnaZnaGame.setIndexOfTheCurrentQuestionPlayerOne(koZnaZnaGame.getIndexOfTheCurrentQuestionPlayerOne()+1);
        }
        else {
            koZnaZnaGame.setIndexOfTheCurrentQuestionPlayerTwo(koZnaZnaGame.getIndexOfTheCurrentQuestionPlayerTwo()+1);
        }
    }
}
