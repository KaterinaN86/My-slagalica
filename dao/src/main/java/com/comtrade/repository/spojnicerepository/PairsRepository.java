package com.comtrade.repository.spojnicerepository;

import com.comtrade.model.spojnicemodel.PairsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PairsRepository extends JpaRepository<PairsModel, Long> {

}

