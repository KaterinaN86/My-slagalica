package com.comtrade.model.asocijacijamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitFieldName {
    private Long gameId;
    private String fieldName;

    public SubmitFieldName() {
    }

    public SubmitFieldName(Long gameId, String fieldName) {
        this.gameId = gameId;
        this.fieldName = fieldName;
    }
}
