package com.comtrade.model.skockomodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SkockoResponseWithPositions extends SkockoResponse {
    private Integer goodPositions;
    private Integer badPositions;

    public SkockoResponseWithPositions(boolean isWinning, Integer goodPositions, Integer badPositions) {
        super(isWinning);
        this.goodPositions = goodPositions;
        this.badPositions = badPositions;
    }
}
