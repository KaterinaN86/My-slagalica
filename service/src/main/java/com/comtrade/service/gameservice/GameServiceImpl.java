package com.comtrade.service.gameservice;

import com.comtrade.interfaces.MultiPlayerService;
import com.comtrade.interfaces.OnePlayerGameService;
import com.comtrade.model.Games;
import com.comtrade.model.IsActive;
import com.comtrade.model.games.Game;
import com.comtrade.model.games.OnePlayerGame;
import com.comtrade.model.games.OnePlayerInitResponse;
import com.comtrade.model.Points;
import com.comtrade.model.Timers;
import com.comtrade.model.games.TwoPlayerGame;
import com.comtrade.model.user.User;
import com.comtrade.repository.*;
import com.comtrade.repository.gamerepository.OnePlayerGameRepository;
import com.comtrade.repository.gamerepository.TwoPlayerGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Slf4j
@Service
public class GameServiceImpl implements OnePlayerGameService, MultiPlayerService {

    private final OnePlayerGameRepository onePlayerGameRepository;
    private final TwoPlayerGameRepository twoPlayerGameRepository;
    private final UserRepository userRepository;
    private final GamesRepository gamesRepository;
    private final PointsRepository pointsRepository;
    private final TimersRepository timersRepository;
    private final IsActiveRepository isActiveRepository;

    private List<User> playerQueue = new ArrayList<>();

    private List<TwoPlayerGame> playerGames = new ArrayList<>();

    public GameServiceImpl(TwoPlayerGameRepository twoPlayerGameRepository, OnePlayerGameRepository onePlayerGameRepository, UserRepository userRepository, GamesRepository gamesRepository, PointsRepository pointsRepository, TimersRepository timersRepository, IsActiveRepository isActiveRepository) {
        this.onePlayerGameRepository = onePlayerGameRepository;
        this.twoPlayerGameRepository = twoPlayerGameRepository;
        this.userRepository = userRepository;
        this.gamesRepository = gamesRepository;
        this.pointsRepository = pointsRepository;
        this.timersRepository = timersRepository;
        this.isActiveRepository = isActiveRepository;
    }

    @Override
    public OnePlayerGame createNewOnePlayerGame(Principal principal) throws Exception {
        Optional<User> user=userRepository.findByUserName(principal.getName());
        if(user.isEmpty()){
            throw new Exception("User not found");
        }
        Games games=new Games();
        gamesRepository.save(games);
        Timers timers=new Timers();
        timersRepository.save(timers);
        IsActive isActive = new IsActive();
        isActiveRepository.save(isActive);
        OnePlayerGame onePlayerGame =new OnePlayerGame(user.get(),games, timers, isActive);
        Points points=new Points();
        pointsRepository.save(points);
        onePlayerGame.setPoints(points);
        OnePlayerGame game= onePlayerGameRepository.save(onePlayerGame);
        return game;
    }

    @Override
    public Game getGame(Principal principal) throws Exception {
        List<OnePlayerGame> onePlayerGames = onePlayerGameRepository.findAllByUserUserNameAndFinishedFalse(principal.getName());
        List<TwoPlayerGame> twoPlayerGames = twoPlayerGameRepository.findByUserName(principal.getName());
        System.out.println(onePlayerGames);
        System.out.println(twoPlayerGames);
        if (!onePlayerGames.isEmpty()){
            return onePlayerGames.get(0);
        }
        else if(!twoPlayerGames.isEmpty()){
            return twoPlayerGames.get(0);
        }
        return createNewOnePlayerGame(principal);
    }
    @Override
    public OnePlayerInitResponse getOnePlayerGameInitData(Principal principal) throws Exception {
        Game game= getGame(principal);
        OnePlayerInitResponse response=new OnePlayerInitResponse();
        response.setMsg("");

        response.setNumOfPointsSum(game.getPoints(principal).getSumOfPoints());

        response.setNumOfPointsSlagalica(game.getPoints(principal).getNumOfPointsSlagalica());
        response.setNumOfPointsMojBroj(game.getPoints(principal).getNumOfPointsMojBroj());
        response.setNumOfPointsSkocko(game.getPoints(principal).getNumOfPointsSkocko());
        response.setNumOfPointsSpojnice(game.getPoints(principal).getNumOfPointsSpojnice());
        response.setNumOfPointsKoZnaZna(game.getPoints(principal).getNumOfPointsKoZnaZna());
        response.setNumOfPointsAsocijacija((int)game.getPoints(principal).getNumOfPointsAsocijacije());

        response.setActiveSlagalica(game.getIsActive(principal).isActiveSlagalica());
        response.setActiveMojBroj(game.getIsActive(principal).isActiveMojBroj());
        response.setActiveSkocko(game.getIsActive(principal).isActiveSkocko());
        response.setActiveSpojnice(game.getIsActive(principal).isActiveSpojnice());
        response.setActiveKoZnaZna(game.getIsActive(principal).isActiveKoZnaZna());
        response.setActiveAsocijacije(game.getIsActive(principal).isActiveAsocijacije());

        return response;
    }

    @Override
    public List<OnePlayerGame> getTopTenOnePlayerGames() {
        ArrayList<OnePlayerGame> lisOfGames= (ArrayList<OnePlayerGame>) onePlayerGameRepository.findAllOrderedBySumOfPoints();
        return lisOfGames;
    }

    @Override
    public void finishOnePlayerGame(Principal principal) throws Exception {
        Game game = getGame(principal);
        game.setFinished(true);

        if(game instanceof OnePlayerGame){
            onePlayerGameRepository.save((OnePlayerGame) game);
        }
        if(game instanceof TwoPlayerGameRepository){
            twoPlayerGameRepository.save((TwoPlayerGame) game);
        }
    }





    @Override
    public boolean addPlayerToQueue(Principal principal) {
        if (isInGame(principal)){
            log.info("Korinsik je vec u gejmu");
            return false;
        }
        Optional<User> optUser=userRepository.findByUserName(principal.getName());
        if (!playerQueue.contains(optUser.get())){
            playerQueue.add(optUser.get());
            createTwoPlayerGame();
            log.info("Queue is: "+playerQueue.toString());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removePlayerFromQueue(Principal principal) {
        Optional<User> optUser=userRepository.findByUserName(principal.getName());
        if (playerQueue.contains(optUser.get())) {
            playerQueue.remove(optUser.get());
            log.info(playerQueue.toString());
            return true;
        }
        return false;
    }

    @Override
    public void createTwoPlayerGame() {
        if (playerQueue.size() >= 2) {
            User user1 = playerQueue.get(0);
            User user2 = playerQueue.get(1);
            playerQueue.remove(user1);
            playerQueue.remove(user2);
            Games games=new Games();
            gamesRepository.save(games);
            Timers timers1=new Timers();
            Timers timers2=new Timers();
            timersRepository.save(timers1);
            timersRepository.save(timers2);
            IsActive isActive1 = new IsActive();
            IsActive isActive2 = new IsActive();
            isActiveRepository.save(isActive1);
            isActiveRepository.save(isActive2);
            TwoPlayerGame twoPlayerGame = new TwoPlayerGame(isActive1, isActive2, user1, user2, games, timers1, timers2);
            Points points1=new Points();
            Points points2=new Points();
            pointsRepository.save(points1);
            pointsRepository.save(points2);
            twoPlayerGame.setPoints1(points1);
            twoPlayerGame.setPoints2(points2);
            twoPlayerGame.setFinished(false);
            twoPlayerGameRepository.save(twoPlayerGame);
            log.info("twoPlayerGame ccreated");
        } else {
            log.info("Not enough player in queue");
        }
    }

    @Override
    public boolean isInGame(Principal principal) {
        List<TwoPlayerGame> listOfGames = twoPlayerGameRepository.findByUserName(principal.getName());
        if (!listOfGames.isEmpty()){
            return true;
        }
        return false;
    }
}