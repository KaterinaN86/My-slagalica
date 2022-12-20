package com.comtrade.service.gameservice;

import com.comtrade.model.Games;
import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.OnePlayerGame.OnePlayerInitResponse;
import com.comtrade.model.Points;
import com.comtrade.model.user.User;
import com.comtrade.repository.GamesRepository;
import com.comtrade.repository.PointsRepository;
import com.comtrade.repository.UserRepository;
import com.comtrade.repository.gamerepository.OnePlayerGameRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class OnePlayerOnePlayerGameServiceImpl implements OnePlayerGameService {

    private final OnePlayerGameRepository onePlayerGameRepository;
    private final UserRepository userRepository;
    private final GamesRepository gamesRepository;
    private final PointsRepository pointsRepository;

    public OnePlayerOnePlayerGameServiceImpl(OnePlayerGameRepository onePlayerGameRepository, UserRepository userRepository, GamesRepository gamesRepository, PointsRepository pointsRepository) {
        this.onePlayerGameRepository = onePlayerGameRepository;
        this.userRepository = userRepository;
        this.gamesRepository = gamesRepository;
        this.pointsRepository = pointsRepository;
    }

    @Override
    public OnePlayerGame createNewGame(Principal principal) throws Exception {
        Optional<User> user=userRepository.findByUserName(principal.getName());
        if(user.isEmpty()){
            throw new Exception("User not found");
        }
        Games games=new Games();
        gamesRepository.save(games);
        OnePlayerGame onePlayerGame =new OnePlayerGame(user.get(),games);
        Points points=new Points();
        pointsRepository.save(points);
        onePlayerGame.setPoints(points);
        OnePlayerGame game= onePlayerGameRepository.save(onePlayerGame);
        return game;
    }

    @Override
    public OnePlayerGame getGame(Principal principal) throws Exception {
        List<OnePlayerGame> games = onePlayerGameRepository.findAllByUserUserNameAndFinishedFalse(principal.getName());
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

        response.setNumOfPointsSum(game.getPoints().getSumOfPoints());

        response.setNumOfPointsSlagalica(game.getPoints().getNumOfPointsSlagalica());
        response.setNumOfPointsMojBroj(game.getPoints().getNumOfPointsMojBroj());
        response.setNumOfPointsSkocko(game.getPoints().getNumOfPointsSkocko());
        response.setNumOfPointsSpojnice(game.getPoints().getNumOfPointsSpojnice());
        response.setNumOfPointsKoZnaZna(game.getPoints().getNumOfPointsKoZnaZna());
        response.setNumOfPointsAsocijacija((int)game.getPoints().getNumOfPointsAsocijacije());
        return response;
    }

    @Override
    public List<OnePlayerGame> getTopTen() {
        ArrayList<OnePlayerGame> lisOfGames= (ArrayList<OnePlayerGame>) onePlayerGameRepository.findByFinishedTrueOrderByNumOfPointsDesc();
        return  lisOfGames;
    }

    public void finishedGame(Principal principal) throws Exception {

        OnePlayerGame game = getGame(principal);
        game.setFinished(true);
        onePlayerGameRepository.save(game);

    }
}