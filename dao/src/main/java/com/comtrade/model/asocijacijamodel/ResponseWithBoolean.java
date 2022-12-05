package com.comtrade.model.asocijacijamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWithBoolean extends Response{
    private boolean correct;

    public ResponseWithBoolean(boolean correct) {
        this.correct = correct;
    }
}
