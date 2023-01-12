package com.comtrade.model.games;

import com.comtrade.model.IsActive;
import com.comtrade.model.Points;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TwoPlayerInitResponse {

    String username1;
    String username2;

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

    public TwoPlayerInitResponse(Points points1, IsActive isActive, Points points2, String username1,String username2) {

        this.username1=username1;
        this.username2=username2;

        this.numOfPointsSum1 = points1.getSumOfPoints();
        this.numOfPointsSlagalica1 = points1.getNumOfPointsSlagalica();
        this.numOfPointsMojBroj1 = points1.getNumOfPointsMojBroj();
        this.numOfPointsSkocko1 = points1.getNumOfPointsSkocko();
        this.numOfPointsSpojnice1 = points1.getNumOfPointsSpojnice();
        this.numOfPointsKoZnaZna1 = points1.getNumOfPointsKoZnaZna();
        this.numOfPointsAsocijacija1 = (int) points1.getNumOfPointsAsocijacije();

        this.numOfPointsSum2 = points2.getSumOfPoints();
        this.numOfPointsSlagalica2 = points2.getNumOfPointsSlagalica();
        this.numOfPointsMojBroj2 = points2.getNumOfPointsMojBroj();
        this.numOfPointsSkocko2 = points2.getNumOfPointsSkocko();
        this.numOfPointsSpojnice2 = points2.getNumOfPointsSpojnice();
        this.numOfPointsKoZnaZna2 = points2.getNumOfPointsKoZnaZna();
        this.numOfPointsAsocijacija2 = (int) points2.getNumOfPointsAsocijacije();

        this.isActiveSlagalica = isActive.isActiveSlagalica();
        this.isActiveMojBroj = isActive.isActiveMojBroj();
        this.isActiveSkocko = isActive.isActiveSkocko();
        this.isActiveSpojnice = isActive.isActiveSpojnice();
        this.isActiveKoZnaZna = isActive.isActiveKoZnaZna();
        this.isActiveAsocijacije = isActive.isActiveAsocijacije();
    }

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
