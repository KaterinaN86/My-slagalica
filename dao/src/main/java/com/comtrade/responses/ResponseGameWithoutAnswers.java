package com.comtrade.responses;

import com.comtrade.model.koznaznamodel.KoZnaZnaGame;
import com.comtrade.model.koznaznamodel.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseGameWithoutAnswers extends Response{
    KoZnaZnaGame koZnaZnaGame;

    public ResponseGameWithoutAnswers(KoZnaZnaGame koZnaZnaGame) {
        List<Question> questions=new ArrayList<>();
        for (Question question: koZnaZnaGame.getQuestions()) {
            question.setCorrectAnswer(0);
            questions.add(question);
        }
        koZnaZnaGame.setQuestions(questions);
        this.koZnaZnaGame = koZnaZnaGame;
    }
}
