package com.comtrade.model.spojnicemodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooleanResponse extends Response {

    private boolean exact;

    public BooleanResponse(boolean exact) {
        this.exact = exact;
    }
}
