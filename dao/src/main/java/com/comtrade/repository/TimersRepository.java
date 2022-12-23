package com.comtrade.repository;

import com.comtrade.model.Timers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimersRepository extends JpaRepository<Timers,Long> {
}
