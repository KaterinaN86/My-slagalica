package com.comtrade.model.skockomodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SkockoResponseWithNumberOfPoints extends SkockoResponse {

    private Integer points;
    private List<Integer> combination;

    public SkockoResponseWithNumberOfPoints(boolean isWinning, Integer points, List<Integer> combination) {
        super(isWinning);
        this.points = points;
        this.combination = combination;
    }
}
