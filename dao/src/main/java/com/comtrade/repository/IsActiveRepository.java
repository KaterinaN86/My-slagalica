package com.comtrade.repository;

import com.comtrade.model.IsActive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IsActiveRepository extends JpaRepository<IsActive, Long> {

}
