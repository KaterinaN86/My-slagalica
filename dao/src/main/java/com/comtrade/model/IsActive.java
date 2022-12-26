package com.comtrade.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@AllArgsConstructor
@Entity
@ToString
public class IsActive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    boolean isActiveSlagalica;
    boolean isActiveSkocko;
    boolean isActiveMojBroj;
    boolean isActiveKoZnaZna;
    boolean isActiveSpojnice;
    boolean isActiveAsocijacije;

    public IsActive() {
        this.isActiveSlagalica = true;
        this.isActiveSkocko = true;
        this.isActiveMojBroj = true;
        this.isActiveKoZnaZna = true;
        this.isActiveSpojnice = true;
        this.isActiveAsocijacije = true;
    }
}
