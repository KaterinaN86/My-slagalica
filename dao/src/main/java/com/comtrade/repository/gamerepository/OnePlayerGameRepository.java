package com.comtrade.repository.gamerepository;

import com.comtrade.model.games.OnePlayerGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OnePlayerGameRepository extends JpaRepository<OnePlayerGame,Long> {

    List<OnePlayerGame> findAllByUserUserNameAndFinishedFalse(String userName);
    @Query("SELECT opg from OnePlayerGame opg where opg.finished=true order by (opg.points.numOfPointsSlagalica+" +
            "opg.points.numOfPointsMojBroj+" +
            "opg.points.numOfPointsSkocko+" +
            "opg.points.numOfPointsSpojnice+" +
            "opg.points.numOfPointsKoZnaZna+" +
            "opg.points.numOfPointsAsocijacije) DESC")
    List<OnePlayerGame> findAllOrderedBySumOfPoints();
}

