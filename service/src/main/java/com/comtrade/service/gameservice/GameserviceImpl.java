package com.comtrade.service.gameservice;

import com.comtrade.model.OnePlayerGame;
import com.comtrade.model.user.User;
import com.comtrade.repository.UserRepository;
import com.comtrade.repository.gamerepository.Gamerepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class GameserviceImpl implements Gameservice {

    private final Gamerepository gamerepository;
    private final UserRepository userRepository;

    public GameserviceImpl(Gamerepository gamerepository, UserRepository userRepository) {
        this.gamerepository = gamerepository;
        this.userRepository = userRepository;
    }

    @Override
    public OnePlayerGame createNewGame(Principal principal) throws Exception {
        Optional<User> user=userRepository.findByUserName(principal.getName());
        if(user.isEmpty()){
            throw new Exception("User not found");
        }
        OnePlayerGame game=gamerepository.save(new OnePlayerGame(user.get()));
        return game;
    }

    @Override
    public OnePlayerGame getGame(Principal principal) throws Exception {
        List<OnePlayerGame> games = gamerepository.findAllByUserUserNameAndFinishedFalse(principal.getName());
        if (!games.isEmpty()){
            return games.get(0);
        }
        return createNewGame(principal);
    }
}
