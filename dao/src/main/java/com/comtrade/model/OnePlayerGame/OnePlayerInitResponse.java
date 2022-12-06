package com.comtrade.model.OnePlayerGame;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OnePlayerInitResponse {
    String msg;
    int numOfPointsSum;
    int numOfPointsSlagalica;
    int numOfPointsMojBroj;
    int numOfPointsSkocko;
    int numOfPointsSpojnice;
    int numOfPointsKoZnaZna;
    int numOfPointsAsocijacije;
    public OnePlayerInitResponse(){
        msg="";
        numOfPointsSum=0;
        numOfPointsSlagalica=0;
        numOfPointsMojBroj=0;
        numOfPointsSkocko=0;
        numOfPointsSpojnice=0;
        numOfPointsKoZnaZna=0;
        numOfPointsAsocijacije=0;
    }
}
