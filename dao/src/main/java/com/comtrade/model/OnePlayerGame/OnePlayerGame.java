package com.comtrade.model.OnePlayerGame;

import com.comtrade.model.Games;
import com.comtrade.model.Points;
import com.comtrade.model.Timers;
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

    @OneToOne
    private Points points;

    @OneToOne
    User user;

    @OneToOne
    Games games;

    @OneToOne
    Timers timers;

    public OnePlayerGame(User user, Games games, Timers timers){
        this.user=user;
        this.games=games;
        this.timers=timers;
        finished=false;
    }

}
