package com.comtrade.model.spojnicemodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitRelatedPairs {

    private Long gameId;
    private Double numberOfRelatedPairs;

    public SubmitRelatedPairs(Long gameId, Double numberOfRelatedPairs) {
        this.gameId = gameId;
        this.numberOfRelatedPairs = numberOfRelatedPairs;
    }

    public SubmitRelatedPairs(){}
}
