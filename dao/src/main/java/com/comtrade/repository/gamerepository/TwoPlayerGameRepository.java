package com.comtrade.repository.gamerepository;

import com.comtrade.model.games.TwoPlayerGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwoPlayerGameRepository extends JpaRepository<TwoPlayerGame, Long> {



}
