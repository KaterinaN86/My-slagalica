package com.comtrade.model.OnePlayerGame;

import lombok.Data;

@Data
public class RangListResponse {
    private String userName;
    private Integer numberOfPoints;

    public RangListResponse(OnePlayerGame onePlayerGame) {
        this.userName=onePlayerGame.user.getUserName();
        this.numberOfPoints=onePlayerGame.getNumOfPoints();
    }
}
