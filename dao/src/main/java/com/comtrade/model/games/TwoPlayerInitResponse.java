package com.comtrade.model.games;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TwoPlayerInitResponse {

    int numOfPointsSum1;
    int numOfPointsSlagalica1;
    int numOfPointsMojBroj1;
    int numOfPointsSkocko1;
    int numOfPointsSpojnice1;
    int numOfPointsKoZnaZna1;
    int numOfPointsAsocijacija1;

    int numOfPointsSum2;
    int numOfPointsSlagalica2;
    int numOfPointsMojBroj2;
    int numOfPointsSkocko2;
    int numOfPointsSpojnice2;
    int numOfPointsKoZnaZna2;
    int numOfPointsAsocijacija2;

    boolean isActiveSlagalica;
    boolean isActiveMojBroj;
    boolean isActiveSkocko;
    boolean isActiveSpojnice;
    boolean isActiveKoZnaZna;
    boolean isActiveAsocijacije;

    public TwoPlayerInitResponse(){
        numOfPointsSum1 = 0;
        numOfPointsSlagalica1 = 0;
        numOfPointsMojBroj1 = 0;
        numOfPointsSkocko1 = 0;
        numOfPointsSpojnice1 = 0;
        numOfPointsKoZnaZna1 = 0;
        numOfPointsAsocijacija1 = 0;

        numOfPointsSum2 = 0;
        numOfPointsSlagalica2 = 0;
        numOfPointsMojBroj2 = 0;
        numOfPointsSkocko2 = 0;
        numOfPointsSpojnice2 = 0;
        numOfPointsKoZnaZna2 = 0;
        numOfPointsAsocijacija2 = 0;

        boolean isActiveSlagalica=true;
        boolean isActiveMojBroj=true;
        boolean isActiveSkocko=true;
        boolean isActiveSpojnice=true;
        boolean isActiveKoZnaZna=true;
        boolean isActiveAsocijacije=true;
    }

}
