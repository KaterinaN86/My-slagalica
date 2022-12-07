package com.comtrade.model.koznaznamodel;

import lombok.Data;

@Data
public class SubmitQuestion {
    private Long gameId;
    private Integer questionIndex;
    private Long questionId;
    private Integer selectedQuestion;
}
