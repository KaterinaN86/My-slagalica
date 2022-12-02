package com.comtrade.model.asocijacijamodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String columna;
    private String columnb;
    private String columnc;
    private String columnd;
    private String finalWord;

}
