package com.comtrade.model.asocijacijamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWithGameId extends Response{
    private Long gameId;

    public ResponseWithGameId(Long gameId) {
        this.gameId = gameId;
    }
}
