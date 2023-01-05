package com.comtrade.model.games;

import com.comtrade.model.Games;
import com.comtrade.model.IsActive;
import com.comtrade.model.Points;
import com.comtrade.model.Timers;
import com.comtrade.model.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TwoPlayerGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean finished;

    @OneToOne
    private IsActive isActive1;

    @OneToOne
    private IsActive isActive2;

    @OneToOne
    private Points points1;

    @OneToOne
    private Points points2;

    @OneToOne
    User user1;

    @OneToOne
    User user2;

    @OneToOne
    Games games;

    @OneToOne
    Timers timers1;

    @OneToOne
    Timers timers2;

    public TwoPlayerGame(IsActive isActive1, IsActive isActive2, User user1, User user2, Games games, Timers timers1, Timers timers2) {
        this.isActive1 = isActive1;
        this.isActive2 = isActive2;
        this.user1 = user1;
        this.user2 = user2;
        this.games = games;
        this.timers1 = timers1;
        this.timers2 = timers2;
    }
}
