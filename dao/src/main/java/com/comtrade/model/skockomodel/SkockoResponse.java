package com.comtrade.model.skockomodel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class SkockoResponse {
    private boolean isWinning;

    public SkockoResponse(boolean isWinning) {
        this.isWinning = isWinning;
    }
}
