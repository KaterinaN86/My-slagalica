package com.comtrade.service.spojniceservice;

import com.comtrade.model.OnePlayerGame.OnePlayerGame;
import com.comtrade.model.spojnicemodel.PairsModel;
import com.comtrade.model.spojnicemodel.SpojniceGame;
import com.comtrade.repository.gamerepository.OnePlayerGameRepository;
import com.comtrade.repository.spojnicerepository.PairsRepository;
import com.comtrade.repository.spojnicerepository.SpojniceRepository;
import com.comtrade.service.gameservice.OnePlayerOnePlayerGameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@Slf4j
public class SpojniceServiceImpl implements SpojniceService{

    private final SpojniceRepository spojniceRepository;
    private final PairsRepository pairsRepository;
    private final OnePlayerGameRepository onePlayerGameRepository;
    private final OnePlayerOnePlayerGameServiceImpl gameService;

    public SpojniceServiceImpl(SpojniceRepository spojniceRepository, PairsRepository pairsRepository, OnePlayerGameRepository onePlayerGameRepository, OnePlayerOnePlayerGameServiceImpl gameService) {
        this.spojniceRepository = spojniceRepository;
        this.pairsRepository = pairsRepository;
        this.onePlayerGameRepository = onePlayerGameRepository;
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
            spojniceGame.setActive(true);
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
        if(game.getGames().getSpojniceGame()!=null){
            return game.getGames().getSpojniceGame();
        }else{
            SpojniceGame spojniceGame= createNewSpojniceGame(principal);
            game.getGames().setSpojniceGame(spojniceGame);
            onePlayerGameRepository.save(game);
            return spojniceGame;
        }
    }


    private Integer calcPoints(SpojniceGame spojniceGame, String json){
        Integer numOfPoints=0;
        JSONObject jsonObject=new JSONObject(json);

        List<String> col1= List.of(spojniceGame.getPairsModel().getColumn1().split(", "));
        List<String> col2= List.of(spojniceGame.getPairsModel().getColumn2().split(", "));

        HashMap<String, String> col2Map = new HashMap<>();
        for(String word:col2){
            col2Map.put(word.split(":")[0],word.split(":")[1]);
        }

        HashMap<String,String> pairs=new HashMap<>();
        for(int i=0;i<col1.size();i++){
            pairs.put(col1.get(i).split(":")[1],col2Map.get(col1.get(i).split(":")[0]));
        }
        for(String word:col1){
            String key=word.split(":")[1];
            try {
                if (jsonObject.get(key).equals(pairs.get(key))){
                    numOfPoints+=3;
                }
            }catch (Exception e){
                //do nothing when user didn't connect all words
            }
        }
        return numOfPoints;
    }

    public Integer getNumberOfPoints(Principal principal, String json) throws Exception {
        OnePlayerGame onePlayerGame = gameService.getGame(principal);
        SpojniceGame spojniceGame = getGame(principal);
        if (!spojniceGame.isActive()){
            return onePlayerGame.getPoints().getNumOfPointsSpojnice();
        }
        Integer points=calcPoints(spojniceGame, json);
        OnePlayerGame game=gameService.getGame(principal);
        game.getPoints().setNumOfPointsSpojnice(points);
        spojniceGame.setActive(false);
        spojniceRepository.save(spojniceGame);

        return onePlayerGame.getPoints().getNumOfPointsSpojnice();
    }


}
