package com.comtrade.repository.asocijacijarepository;

import com.comtrade.model.asocijacijamodel.WordModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<WordModel, Long> {
}
