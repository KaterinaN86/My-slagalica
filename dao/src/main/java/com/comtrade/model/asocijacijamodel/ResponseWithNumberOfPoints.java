package com.comtrade.model.asocijacijamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWithNumberOfPoints extends Response{
    private Double points;

    public ResponseWithNumberOfPoints(Double points) {
        this.points = points;
    }

    public ResponseWithNumberOfPoints() {
    }
}
