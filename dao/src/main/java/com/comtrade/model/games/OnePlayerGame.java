package com.comtrade.model.games;

import com.comtrade.model.Games;
import com.comtrade.model.IsActive;
import com.comtrade.model.Points;
import com.comtrade.model.Timers;
import com.comtrade.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.security.Principal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OnePlayerGame implements Game{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean finished;

    @OneToOne
    private IsActive isActive;

    @OneToOne
    private Points points;

    @OneToOne
    User user;

    @OneToOne
    Games games;

    @OneToOne
    Timers timers;

    public OnePlayerGame(User user, Games games, Timers timers,IsActive isActive){
        this.user=user;
        this.games=games;
        this.timers=timers;
        finished=false;
        this.isActive = isActive;
    }

    @Override
    public Points getPoints(Principal principal) {
        return points;
    }

    @Override
    public void setPoints(Principal principal, Points points) {
        this.points=points;
    }

    @Override
    public Timers getTimers(Principal principal) {
        return timers;
    }

    @Override
    public void setTimers(Principal principal, Timers timers) {
        this.timers=timers;
    }

    @Override
    public IsActive getIsActive(Principal principal) {
        return isActive;
    }

    @Override
    public void setIsActive(Principal principal, IsActive isActive) {
        this.isActive=isActive;
    }

    @Override
    public void setFinished(Boolean finished) {
        this.finished=finished;
    }
}
