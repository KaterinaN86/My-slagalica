package com.comtrade.service.gameservice;

import com.comtrade.interfaces.MultiPlayerService;
import com.comtrade.model.Games;
import com.comtrade.model.IsActive;
import com.comtrade.model.Points;
import com.comtrade.model.Timers;
import com.comtrade.model.games.TwoPlayerGame;
import com.comtrade.model.user.User;
import com.comtrade.repository.*;
import com.comtrade.repository.gamerepository.TwoPlayerGameRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MultiPlayerServiceImpl implements MultiPlayerService {

    private final TwoPlayerGameRepository twoPlayerGameRepository;
    private final UserRepository userRepository;
    private final GamesRepository gamesRepository;
    private final PointsRepository pointsRepository;
    private final TimersRepository timersRepository;
    private final IsActiveRepository isActiveRepository;

    private List<User> playerQueue = new ArrayList<>();

    private List<TwoPlayerGame> playerGames = new ArrayList<>();

    public MultiPlayerServiceImpl(TwoPlayerGameRepository twoPlayerGameRepository,
                                  UserRepository userRepository,
                                  GamesRepository gamesRepository,
                                  PointsRepository pointsRepository,
                                  TimersRepository timersRepository,
                                  IsActiveRepository isActiveRepository) {
        this.twoPlayerGameRepository = twoPlayerGameRepository;
        this.userRepository = userRepository;
        this.gamesRepository = gamesRepository;
        this.pointsRepository = pointsRepository;
        this.timersRepository = timersRepository;
        this.isActiveRepository = isActiveRepository;
    }

    @Override
    public String addPlayerToQueue(Principal principal) {
        Optional<User> optUser=userRepository.findByUserName(principal.getName());
        if (optUser.isPresent()){
            if (!playerQueue.contains(optUser.get())) {
                playerQueue.add(optUser.get());
            }
            return "Player added to queue!";
        }
        return "Something went wrong!?";
    }

    public void removePlayerFromQueue(Principal principal) {
        Optional<User> optUser=userRepository.findByUserName(principal.getName());
        playerQueue.remove(optUser.get());
    }

    @Override
    public void createTwoPlayerGame(Principal principal) {
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
            playerGames.add(twoPlayerGame);
            playerQueue.remove(user1);
            playerQueue.remove(user2);
        }
    }

}
