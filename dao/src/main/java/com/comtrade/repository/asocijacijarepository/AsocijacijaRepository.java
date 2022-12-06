package com.comtrade.repository.asocijacijarepository;

import com.comtrade.model.asocijacijamodel.AsocijacijaGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsocijacijaRepository extends JpaRepository<AsocijacijaGame, Long> {
}
