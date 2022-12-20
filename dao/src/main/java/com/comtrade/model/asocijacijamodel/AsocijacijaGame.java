package com.comtrade.model.asocijacijamodel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class AsocijacijaGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "rijeci_id")
    private WordModel wordModel;
    private boolean isActive;

    public AsocijacijaGame(){
        this.isActive = true;
    }

    public AsocijacijaGame(Long id, WordModel wordModel) {
        this.id = id;
        this.wordModel = wordModel;
        this.isActive = true;
    }
}
