package com.comtrade.model;

import com.comtrade.model.asocijacijamodel.AsocijacijaModel;
import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.model.skockomodel.SkockoGame;
import com.comtrade.model.slagalicamodel.Slagalica;
import com.comtrade.model.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class OnePlayerGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean finished;
    private Integer numOfPoints;
    @OneToOne
    User user;
    @OneToOne
    Slagalica slagalica;

    @OneToOne
    MojBrojGame mojBrojGame;

    @OneToOne
    SkockoGame skockoGame;
    //ko zna zna
    //spojnice
    @OneToOne
    AsocijacijaModel asocijacijaModel;
    public OnePlayerGame(User user){
        this.user=user;
        finished=false;
        numOfPoints=0;
    }

}
