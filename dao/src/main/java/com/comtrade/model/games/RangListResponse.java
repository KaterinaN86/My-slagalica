package com.comtrade.model.games;

import lombok.Data;

@Data
public class RangListResponse {
    private String userName;
    private Integer numberOfPoints;

    public RangListResponse(OnePlayerGame onePlayerGame) {
        this.userName=onePlayerGame.user.getUserName();
        this.numberOfPoints=onePlayerGame.getPoints().getSumOfPoints();
    }
}
