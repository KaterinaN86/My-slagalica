package com.comtrade.model.slagalicamodel;

import lombok.Data;

@Data
public class SubmitResponse {
    private String longestWord;
    private Integer points;

    public SubmitResponse(String longestWord, Integer points) {
        this.longestWord = longestWord;
        this.points = points;
    }
}
