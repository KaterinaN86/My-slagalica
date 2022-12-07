package com.comtrade.service.koznaznaservice;

import com.comtrade.model.koznaznamodel.KoZnaZnaGame;
import com.comtrade.model.koznaznamodel.Question;
import com.comtrade.model.koznaznamodel.responses.AnswerResponse;
import com.comtrade.model.koznaznamodel.responses.Response;
import com.comtrade.repository.koznaznarepository.KoZnaZnaRepository;
import com.comtrade.repository.koznaznarepository.QuestionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KoZnaZnaServiceImpl {
        private QuestionRepository questionRepository;
        private KoZnaZnaRepository koZnaZnaRepository;

    public KoZnaZnaServiceImpl(QuestionRepository questionRepository, KoZnaZnaRepository koZnaZnaRepository) {
        this.questionRepository = questionRepository;
        this.koZnaZnaRepository = koZnaZnaRepository;
    }

    public KoZnaZnaGame createNewGame(){
            KoZnaZnaGame koZnaZnaGame=new KoZnaZnaGame();
            koZnaZnaGame.setQuestions(getRandomQuestions());
            koZnaZnaGame.setActiveGame(true);
            koZnaZnaGame.setIndexOfTheCurrentQuestion(0);
            koZnaZnaGame.setNumberOfPoints(0);
            koZnaZnaRepository.save(koZnaZnaGame);
            return koZnaZnaGame;
    }
        public List<Question> getRandomQuestions(){
            List<Question> questions=new ArrayList<>();
            for (int i: getRandomQuestionIds()) {
                if(questionRepository.getReferenceById((long) i)!=null){
                    questions.add(questionRepository.getReferenceById((long) i));
                }
            }
            return questions;
        }
        public Set<Integer> getRandomQuestionIds(){
            Set<Integer> ids=new TreeSet<>();
            int numberOfQuestions=questionRepository.findAll().size();
            do{
                int random = (int)(Math.random()*numberOfQuestions);
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
            koZnaZnaGame.setIndexOfTheCurrentQuestion(koZnaZnaGame.getIndexOfTheCurrentQuestion()+1);
            if(selectedQuestion.equals(koZnaZnaGame.getQuestions().get(questionIndex).getCorrectAnswer())){
                koZnaZnaGame.setNumberOfPoints(koZnaZnaGame.getNumberOfPoints()+3);
            } else if (!selectedQuestion.equals(0)) {
                koZnaZnaGame.setNumberOfPoints(koZnaZnaGame.getNumberOfPoints()-1);
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
        return koZnaZnaGame.getNumberOfPoints();
    }
}
