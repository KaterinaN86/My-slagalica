package com.comtrade.model.skockomodel;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SkockoResponseWithPositions extends SkockoResponse {
    private Integer goodPositions;
    private Integer badPositions;

    public SkockoResponseWithPositions(boolean isWinning, Integer goodPositions, Integer badPositions) {
        super(isWinning);
        this.goodPositions = goodPositions;
        this.badPositions = badPositions;
    }
}
