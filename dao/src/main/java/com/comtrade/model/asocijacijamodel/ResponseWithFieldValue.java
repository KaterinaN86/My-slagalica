package com.comtrade.model.asocijacijamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWithFieldValue extends Response{
    private String fieldValue;

    public ResponseWithFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
