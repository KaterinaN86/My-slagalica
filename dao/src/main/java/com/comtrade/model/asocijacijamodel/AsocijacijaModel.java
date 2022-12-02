package com.comtrade.model.asocijacijamodel;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class AsocijacijaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "rijeci_id")
    private WordModel wordModel;
    private boolean isActive;
    private Double points;

    public AsocijacijaModel(){
        this.isActive = true;
        this.points = 4.0;
    }

    public AsocijacijaModel(Long id, WordModel wordModel) {
        this.id = id;
        this.wordModel = wordModel;
        this.isActive = true;
        this.points = 4.0;
    }
}
