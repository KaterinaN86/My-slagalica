package com.comtrade.repository.skockorepository;

import com.comtrade.model.skockomodel.SkockoGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkockoGameRepository extends JpaRepository<SkockoGame, Long> {
}
