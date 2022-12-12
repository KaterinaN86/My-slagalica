package com.comtrade.service.spojniceservice;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.spojnicemodel.PairsModel;
import com.comtrade.model.spojnicemodel.SpojniceGame;
import com.comtrade.repository.gamerepository.Gamerepository;
import com.comtrade.repository.spojnicerepository.PairsRepository;
import com.comtrade.repository.spojnicerepository.SpojniceRepository;
import com.comtrade.service.gameservice.GameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class SpojniceServiceImpl implements SpojniceService{

    private final SpojniceRepository spojniceRepository;
    private final PairsRepository pairsRepository;
    private final Gamerepository gamerepository;
    private final GameServiceImpl gameService;

    public SpojniceServiceImpl(SpojniceRepository spojniceRepository, PairsRepository pairsRepository, Gamerepository gamerepository, GameServiceImpl gameService) {
        this.spojniceRepository = spojniceRepository;
        this.pairsRepository = pairsRepository;
        this.gamerepository = gamerepository;
        this.gameService = gameService;
    }

    @Override
    public PairsModel getRandomPairsModel() throws NoSuchElementException {

        int randomId = (int)(Math.floor((Math.random()*pairsRepository.count() + 1)));
        log.info("Find a pairs model with id: " + randomId);
        Optional<PairsModel> randomPairsModel = pairsRepository.findById((long) randomId);
        if(randomPairsModel.isEmpty()){
            log.info("The model with: " + randomId + " id, does not exist!");
            throw new NoSuchElementException();
        }
        log.info("Return model with choosen id: " + randomId);
        return randomPairsModel.get();
    }

    public List<String> getWords(Principal principal) throws Exception {

        SpojniceGame game = getGame(principal);
        List<String> column1 = List.of(game.getPairsModel().getColumn1().split(", "));
        List<String> column2 = List.of(game.getPairsModel().getColumn2().split(", "));
        List<String> words = new ArrayList<>();

        for (String word : column1) {
            words.add(word.split(":")[1]);
        }

        for (String word : column2) {
            words.add(word.split(":")[1]);
        }

        return words;

    }

    @Override
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

    @Override
    public SpojniceGame getGame(Principal principal) throws Exception {
        OnePlayerGame game = gameService.getGame(principal);
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
        SpojniceGame spojniceGame = this.getGame(principal);
        spojniceGame.setActive(false);
        spojniceRepository.save(spojniceGame);
        return spojniceGame.getPoints();
    }


}

