package com.comtrade.model.mojbrojmodel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MojBrojSubmitRequest {
    long gameId;
    String expression;
}
