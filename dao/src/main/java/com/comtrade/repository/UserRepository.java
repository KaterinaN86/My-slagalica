package com.comtrade.repository;


import java.util.List;
import java.util.Optional;

import com.comtrade.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String userName);
}
