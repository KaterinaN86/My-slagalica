package com.comtrade.repository.gamerepository;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface Gamerepository extends JpaRepository<OnePlayerGame,Long> {

    List<OnePlayerGame> findAllByUserUserNameAndFinishedFalse(String userName);
    List<OnePlayerGame> findByFinishedTrueOrderByNumOfPointsDesc();
}

