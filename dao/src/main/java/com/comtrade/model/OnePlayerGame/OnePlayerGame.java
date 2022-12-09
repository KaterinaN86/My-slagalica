package com.comtrade.model.OnePlayerGame;

import com.comtrade.model.asocijacijamodel.AsocijacijaGame;
import com.comtrade.model.koznaznamodel.KoZnaZnaGame;
import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.model.skockomodel.SkockoGame;
import com.comtrade.model.slagalicamodel.SlagalicaGame;
import com.comtrade.model.spojnicemodel.SpojniceGame;
import com.comtrade.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWarDeployment;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OnePlayerGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean finished;
    private Integer numOfPoints;
    @OneToOne
    User user;
    @OneToOne
    SlagalicaGame slagalicaGame;

    @OneToOne
    MojBrojGame mojBrojGame;

    @OneToOne
    SkockoGame skockoGame;

    @OneToOne
    KoZnaZnaGame koZnaZnaGame;

    //spojnice
    @OneToOne
    SpojniceGame spojniceGame;

    @OneToOne
    AsocijacijaGame asocijacijaGame;
    public OnePlayerGame(User user){
        this.user=user;
        finished=false;
        numOfPoints=0;
    }

}
