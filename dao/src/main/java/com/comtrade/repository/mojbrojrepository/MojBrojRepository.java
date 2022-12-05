package com.comtrade.repository.mojbrojrepository;

import com.comtrade.model.mojbrojmodel.MojBrojGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MojBrojRepository extends JpaRepository<MojBrojGame, Long> {

}