package com.comtrade.model.mojbrojmodel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MojBrojSubmitResponse {
    String msg;
    String solution;
    Integer numOfPoints;
}
