package com.comtrade.model.games;

import com.comtrade.model.Games;
import com.comtrade.model.IsActive;
import com.comtrade.model.Points;
import com.comtrade.model.Timers;
import com.comtrade.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.security.Principal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TwoPlayerGame implements Game{

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

    @Override
    public Points getPoints(Principal principal) {
        if (user1.getUserName().equals(principal.getName()) ){
            return points1;
        } else if (user2.getUserName().equals(principal.getName())) {
            return points2;
        }
        throw new RuntimeException("User is not in this game");
    }

    @Override
    public void setPoints(Principal principal, Points points) {
        if (user1.getUserName().equals(principal.getName())){
            this.points1 = points;
        } else if (user2.getUserName().equals(principal.getName())) {
            this.points2 = points;
        }
        throw new RuntimeException("User is not in this game");

    }

    @Override
    public Timers getTimers(Principal principal) {
        if (user1.getUserName().equals(principal.getName())){
            return timers1;
        } else if (user2.getUserName().equals(principal.getName())) {
            return timers2;
        }
        throw new RuntimeException("User is not in this game");
    }

    @Override
    public void setTimers(Principal principal, Timers timers) {
        if (user1.getUserName().equals(principal.getName())){
            this.timers1=timers;
        } else if (user2.getUserName().equals(principal.getName())) {
            this.timers2=timers;

        }
        throw new RuntimeException("User is not in this game");
    }

    @Override
    public IsActive getIsActive(Principal principal) {
        if (user1.getUserName().equals(principal.getName())){
            return isActive1;
        } else if (user2.getUserName().equals(principal.getName())) {
            return isActive2;
        }
        throw new RuntimeException("User is not in this game");
    }

    @Override
    public void setIsActive(Principal principal, IsActive isActive) {
        if (user1.getUserName().equals(principal.getName())){
            this.isActive1 = isActive;
        } else if (user2.getUserName().equals(principal.getName())) {
            this.isActive2 = isActive2;
        }
        throw new RuntimeException("User is not in this game");
    }
}
