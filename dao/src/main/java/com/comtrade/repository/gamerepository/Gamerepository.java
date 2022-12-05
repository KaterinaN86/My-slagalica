package com.comtrade.repository.gamerepository;

import com.comtrade.model.OnePlayerGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Gamerepository extends JpaRepository<OnePlayerGame,Long> {
}
