package com.comtrade.repository.gamerepository;

import com.comtrade.model.games.TwoPlayerGame;
import com.comtrade.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Repository
public interface TwoPlayerGameRepository extends JpaRepository<TwoPlayerGame, Long> {

    @Query("SELECT tpg from TwoPlayerGame tpg WHERE (tpg.user1.userName=?1 OR tpg.user2.userName=?1) AND tpg.finished=false")
    List<TwoPlayerGame> findByUserName(String username);


}
