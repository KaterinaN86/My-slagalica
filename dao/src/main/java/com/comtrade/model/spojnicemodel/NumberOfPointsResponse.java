package com.comtrade.model.spojnicemodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NumberOfPointsResponse extends Response{

    private Integer points;

    public NumberOfPointsResponse(int points) {
        this.points = points;
    }

    public NumberOfPointsResponse(){}
}
