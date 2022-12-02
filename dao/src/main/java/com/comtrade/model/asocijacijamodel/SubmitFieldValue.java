package com.comtrade.model.asocijacijamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitFieldValue extends SubmitFieldName{
    private String word;

    public SubmitFieldValue(Long gameId, String fieldName, String word) {
        super(gameId, fieldName);
        this.word = word;
    }
}
