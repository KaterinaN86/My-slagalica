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
    int numOfPointsAsocijacija;

    boolean isActiveSlagalica;
    boolean isActiveMojBroj;
    boolean isActiveSkocko;
    boolean isActiveSpojnice;
    boolean isActiveKoZnaZna;
    boolean isActiveAsocijacije;

    public OnePlayerInitResponse(){
        msg="";
        numOfPointsSum=0;
        numOfPointsSlagalica=0;
        numOfPointsMojBroj=0;
        numOfPointsSkocko=0;
        numOfPointsSpojnice=0;
        numOfPointsKoZnaZna=0;
        numOfPointsAsocijacija=0;

        boolean isActiveSlagalica=true;
        boolean isActiveMojBroj=true;
        boolean isActiveSkocko=true;
        boolean isActiveSpojnice=true;
        boolean isActiveKoZnaZna=true;
        boolean isActiveAsocijacije=true;
    }


}
