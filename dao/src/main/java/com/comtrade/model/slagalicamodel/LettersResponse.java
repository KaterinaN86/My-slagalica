package com.comtrade.model.slagalicamodel;

import lombok.Data;

@Data
public class LettersResponse {
    private String lettersForFindingTheWord;

    public LettersResponse(String lettersForFindingTheWord) {
        this.lettersForFindingTheWord = lettersForFindingTheWord;
    }
}
