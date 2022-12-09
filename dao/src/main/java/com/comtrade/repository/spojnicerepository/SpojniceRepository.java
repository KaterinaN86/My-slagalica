package com.comtrade.repository.spojnicerepository;

import com.comtrade.model.spojnicemodel.SpojniceGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpojniceRepository extends JpaRepository<SpojniceGame, Long> {
}
