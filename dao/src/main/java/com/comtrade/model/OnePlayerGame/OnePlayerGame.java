package com.comtrade.model.OnePlayerGame;

import com.comtrade.model.Games;
import com.comtrade.model.Points;
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

    private Integer numOfPoints;//todo delete this and calc it as sum of all points

    @OneToOne
    private Points points;

    @OneToOne
    User user;

    @OneToOne
    Games games;

    public OnePlayerGame(User user,Games games){
        this.user=user;
        this.games=games;
        finished=false;
        numOfPoints=0;
    }

}
