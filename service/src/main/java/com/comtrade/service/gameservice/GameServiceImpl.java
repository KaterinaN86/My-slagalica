package com.comtrade.service.gameservice;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.OnePlayerGame.OnePlayerInitResponse;
import com.comtrade.model.user.User;
import com.comtrade.repository.UserRepository;
import com.comtrade.repository.gamerepository.Gamerepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private final Gamerepository gamerepository;
    private final UserRepository userRepository;

    public GameServiceImpl(Gamerepository gamerepository, UserRepository userRepository) {
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
    @Override
    public OnePlayerInitResponse getInitData(Principal principal) throws Exception {
        OnePlayerGame game=getGame(principal);
        OnePlayerInitResponse response=new OnePlayerInitResponse();
        response.setMsg("");
        response.setNumOfPointsSum(game.getNumOfPoints());
        if (game.getMojBrojGame()!=null){
            response.setNumOfPointsMojBroj(game.getMojBrojGame().getNumOfPoints());
        }
        if (game.getSlagalicaGame()!=null){
            response.setNumOfPointsSlagalica(game.getSlagalicaGame().getNumOfPoints());
        }
        return response;
    }
}
