package com.comtrade.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Points {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    int numOfPointsSlagalica=0;
    int numOfPointsMojBroj=0;
    int numOfPointsSkocko=0;
    int numOfPointsSpojnice=0;
    int numOfPointsKoZnaZna=0;
    int numOfPointsAsocijacije=0;

    public int getSumOfPoints(){
        return numOfPointsSlagalica+
                numOfPointsMojBroj+
                numOfPointsSkocko+
                numOfPointsSpojnice+
                numOfPointsKoZnaZna+
                numOfPointsAsocijacije;
    }

}
