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

    public AsocijacijaModel(){
        this.isActive = true;
    }
}
