package com.comtrade.model.spojnicemodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class SpojniceGame {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        @JoinColumn(name = "")
        private PairsModel pairsModel;
        private int points;
        private boolean isActive;

    public SpojniceGame() {}
}

