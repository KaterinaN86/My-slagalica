package com.comtrade.dao.models;


import java.util.Optional;

import com.comtrade.dao.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String userName);

}
