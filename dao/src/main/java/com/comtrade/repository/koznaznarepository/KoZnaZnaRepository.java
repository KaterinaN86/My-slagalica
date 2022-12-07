package com.comtrade.repository.koznaznarepository;


import com.comtrade.model.koznaznamodel.KoZnaZnaGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KoZnaZnaRepository extends JpaRepository<KoZnaZnaGame, Long> {
}
