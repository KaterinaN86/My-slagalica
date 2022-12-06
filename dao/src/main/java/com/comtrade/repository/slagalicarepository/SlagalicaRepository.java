package com.comtrade.repository.slagalicarepository;

import com.comtrade.model.slagalicamodel.SlagalicaGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlagalicaRepository extends JpaRepository<SlagalicaGame, Long> {
}
