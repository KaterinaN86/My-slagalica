package com.comtrade.model.koznaznamodel.responses;

import com.comtrade.model.koznaznamodel.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseQuestionsWithoutAnswer extends Response{
    List<Question> questionList;

    public ResponseQuestionsWithoutAnswer(List<Question> questionsWithAnswers) {
        List<Question> questions=new ArrayList<>();
        for (Question question: questionsWithAnswers) {
            question.setCorrectAnswer(0);
            questions.add(question);
        }
        this.questionList = questions;
    }


}
