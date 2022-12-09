package com.comtrade.service.spojniceservice;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.asocijacijamodel.ResponseWithGameId;
import com.comtrade.model.spojnicemodel.PairsModel;
import com.comtrade.model.spojnicemodel.SpojniceGame;
import com.comtrade.repository.gamerepository.Gamerepository;
import com.comtrade.repository.spojnicerepository.PairsRepository;
import com.comtrade.repository.spojnicerepository.SpojniceRepository;
import com.comtrade.service.gameservice.GameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class SpojniceServiceImpl {

    private final SpojniceRepository spojniceRepository;
    private final PairsRepository pairsRepository;
    private final Gamerepository gamerepository;
    private GameServiceImpl gameService;

    public SpojniceServiceImpl(SpojniceRepository spojniceRepository, PairsRepository pairsRepository, Gamerepository gamerepository) {
        this.spojniceRepository = spojniceRepository;
        this.pairsRepository = pairsRepository;
        this.gamerepository = gamerepository;
    }


    private PairsModel getRandomPairsModel() throws NoSuchElementException {

        int randomId = (int)(Math.floor((Math.random()*pairsRepository.count() + 1)));
        log.info("Find a pairs model with id: " + randomId);
        Optional<PairsModel> randomPairsModel = pairsRepository.findById(Long.valueOf(randomId));
        if(randomPairsModel.isEmpty()){
            log.info("The model with: " + randomId + " id, does not exist!");
            throw new NoSuchElementException();
        }
        log.info("Return model with choosen id: " + randomId);
        return randomPairsModel.get();
    }

    public SpojniceGame createNewSpojniceGame(Principal principal) {

        SpojniceGame spojniceGame = null;
        try {
            log.info("Create new Spojnice game.");
            spojniceGame = new SpojniceGame();
            spojniceGame.setPairsModel(getRandomPairsModel());
            SpojniceGame savedSpojniceGame = spojniceRepository.save(spojniceGame);
            log.info("Created game with id: " + savedSpojniceGame.getId());
            return savedSpojniceGame;
        } catch (NoSuchElementException nEx) {
            log.info("This game is not successfully created!");
            return spojniceGame;
        }
    }

    /*private SpojniceGame chooseOptionalGame(Long gameId) throws NoSuchElementException {
        log.info("Search for game with id:" + gameId);
        Optional<SpojniceGame> optionalSpojniceGame = spojniceRepository.findById(gameId);
        if(optionalSpojniceGame.isEmpty()) {
            log.info("The game with id: " + gameId + "can not be found...");
            throw new NoSuchElementException();
        }
        log.info("Found and return spojnice game with id: " + gameId);
        return optionalSpojniceGame.get();
    }*/
    private PairsModel getRandomPairsGame(Long gameId) throws NoSuchElementException {
        log.info("Search for game with id:" + gameId);
        Optional<PairsModel> randomPairsModel = pairsRepository.findById(gameId);
        if(randomPairsModel.isEmpty()) {
            log.info("The game with id: " + gameId + "can not be found...");
            throw new NoSuchElementException();
        }
        log.info("Found and return spojnice game with id: " + gameId);
        return randomPairsModel.get();
    }

    public SpojniceGame getGame(Principal principal) throws Exception {
        OnePlayerGame game=gameService.getGame(principal);
        if(game.getSpojniceGame()!=null){
            return game.getSpojniceGame();
        }else{
            SpojniceGame spojniceGame= createNewSpojniceGame(principal);
            game.setSpojniceGame(spojniceGame);
            gamerepository.save(game);
            return spojniceGame;
        }
    }

    public Integer getNumberOfPoints(Principal principal) throws Exception {
        SpojniceGame spojniceGame =this.getGame(principal);
        spojniceGame.setActive(false);
        spojniceRepository.save(spojniceGame);
        return spojniceGame.getPoints();
    }


}

