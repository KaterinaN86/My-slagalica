package com.comtrade.service.gameservice;

import com.comtrade.model.Games;
import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.OnePlayerGame.OnePlayerInitResponse;
import com.comtrade.model.user.User;
import com.comtrade.repository.GamesRepository;
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

    public OnePlayerOnePlayerGameServiceImpl(OnePlayerGameRepository onePlayerGameRepository, UserRepository userRepository, GamesRepository gamesRepository) {
        this.onePlayerGameRepository = onePlayerGameRepository;
        this.userRepository = userRepository;
        this.gamesRepository = gamesRepository;
    }

    @Override
    public OnePlayerGame createNewGame(Principal principal) throws Exception {
        Optional<User> user=userRepository.findByUserName(principal.getName());
        if(user.isEmpty()){
            throw new Exception("User not found");
        }
        Games games=new Games();
        gamesRepository.save(games);
        OnePlayerGame game= onePlayerGameRepository.save(new OnePlayerGame(user.get(),games));
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
        response.setNumOfPointsSum(game.getNumOfPoints());
        if (game.getGames().getMojBrojGame()!=null){
            response.setNumOfPointsMojBroj(game.getGames().getMojBrojGame().getNumOfPoints());
        }
        if (game.getGames().getSlagalicaGame()!=null){
            response.setNumOfPointsSlagalica(game.getGames().getSlagalicaGame().getNumOfPoints());
        }if(game.getGames().getSpojniceGame() != null){
            response.setNumOfPointsSpojnice(game.getGames().getSpojniceGame().getPoints());
        }
        if(game.getGames().getSkockoGame() != null) {
            response.setNumOfPointsSkocko(game.getGames().getSkockoGame().getNumOfPoints());
        }
        if(game.getGames().getKoZnaZnaGame() != null) {
            response.setNumOfPointsKoZnaZna(game.getGames().getKoZnaZnaGame().getNumOfPoints());
        }
        if(game.getGames().getSpojniceGame()!= null) {
            response.setNumOfPointsSpojnice(game.getGames().getSpojniceGame().getPoints());
				}
        if(game.getGames().getAsocijacijaGame() != null){
            response.setNumOfPointsAsocijacija((int) game.getGames().getAsocijacijaGame().getNumOfPoints());
        }
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