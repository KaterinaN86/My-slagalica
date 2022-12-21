package com.comtrade.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class Timers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    LocalTime startTimeSlagalica;
    LocalTime startTimeMojBroj;
    LocalTime startTimeSkocko;
    LocalTime startTimeSpojnice;
    LocalTime startTimeKoZnaZna;
    LocalTime startTimeAsocijacije;
    
}
