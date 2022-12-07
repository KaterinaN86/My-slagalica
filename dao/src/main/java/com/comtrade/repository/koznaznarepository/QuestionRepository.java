package com.comtrade.repository.koznaznarepository;

import com.comtrade.model.koznaznamodel.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
