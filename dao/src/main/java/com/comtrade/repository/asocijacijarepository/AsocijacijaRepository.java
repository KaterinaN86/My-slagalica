package com.comtrade.repository.asocijacijarepository;

import com.comtrade.model.asocijacijamodel.AsocijacijaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsocijacijaRepository extends JpaRepository<AsocijacijaModel, Long> {
}
