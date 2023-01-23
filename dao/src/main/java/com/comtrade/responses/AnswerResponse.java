package com.comtrade.responses;

import lombok.Data;

@Data
public class AnswerResponse extends Response{
    private Integer answer;

    public AnswerResponse(Integer correctAnswer) {
        this.answer=correctAnswer;
    }
}
