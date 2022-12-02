package com.comtrade.model.asocijacijamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitNumberOfFields {
    private Long gameId;
    private Double numberOfOpenedFields;

    public SubmitNumberOfFields(Long gameId, Double numberOfOpenedFields) {
        this.gameId = gameId;
        this.numberOfOpenedFields = numberOfOpenedFields;
    }

    public SubmitNumberOfFields() {
    }
}
