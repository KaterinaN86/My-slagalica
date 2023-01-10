package com.comtrade.service.mojbrojservice;


import com.comtrade.model.games.Game;
import com.comtrade.model.games.OnePlayerGame;
import com.comtrade.model.games.TwoPlayerGame;
import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitRequest;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitResponse;
import com.comtrade.repository.gamerepository.OnePlayerGameRepository;
import com.comtrade.repository.gamerepository.TwoPlayerGameRepository;
import com.comtrade.repository.mojbrojrepository.MojBrojRepository;
import com.comtrade.service.gameservice.GameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptException;
import java.security.Principal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MojBrojServiceImpl implements MojBrojService{
    private final MojBrojRepository mojBrojRepository;
    private final OnePlayerGameRepository onePlayerGameRepository;
    private final TwoPlayerGameRepository twoPlayerGameRepository;

    @Autowired
    private GameServiceImpl gameService;
    public MojBrojServiceImpl(MojBrojRepository mojBrojRepository, OnePlayerGameRepository onePlayerGameRepository, TwoPlayerGameRepository twoPlayerGameRepository) {
        this.mojBrojRepository = mojBrojRepository;
        this.onePlayerGameRepository = onePlayerGameRepository;
        this.twoPlayerGameRepository = twoPlayerGameRepository;
    }

    @Override
    public MojBrojGame getGame(Principal principal) throws Exception {
        Game game=gameService.getOnePlayerGame(principal);
        if(game.getGames().getMojBrojGame()!=null){
            return game.getGames().getMojBrojGame();
        }else{
            MojBrojGame MBgame=createNewGame();
            game.getTimers(principal).setStartTimeMojBroj(LocalTime.now());
            game.getGames().setMojBrojGame(MBgame);
            if(game.getClass()==OnePlayerGame.class){
                onePlayerGameRepository.save((OnePlayerGame) game);
            }
            if(game.getClass()== TwoPlayerGame.class){
                twoPlayerGameRepository.save((TwoPlayerGame) game);
            }
            return MBgame;
        }
    }

    @Override
    public MojBrojGame createNewGame(){
        MojBrojGame game=new MojBrojGame();
        List<Integer> nums=game.getNumbers();
        try {
            int target=eval(game.getSolution());
            while(target>999 || target<=0){
                game.initializeRandom();
                target=eval(game.getSolution());
            }
            nums.set(0,target);
        } catch (ScriptException e) {
            game.setSolution("No solution");
        }
        game.setNumbers(nums);
        mojBrojRepository.save(game);
        return game;
    }

    @Override
    public MojBrojGame createNewGame(Long id, ArrayList<Integer> nums, Boolean isActive, String solution){
        MojBrojGame game=new MojBrojGame(id,nums,isActive, solution);
        mojBrojRepository.save(game);
        return game;
    }

    @Override
    public boolean validateExpression(String expr, long gameId) {
        Optional<MojBrojGame> game= mojBrojRepository.findById(gameId);
        if(game.isEmpty()){return false;}
        MojBrojGame existingGame=game.get();
        int cnt=0;
        for(int i=0;i<expr.length();i++){
            if(expr.charAt(i)=='('){
                cnt++;
            } else if (expr.charAt(i)==')') {
                cnt--;
            }
            if(cnt<0){
                return false;
            }
        }
        if (cnt!=0){return false;}
        expr=expr.replaceAll("[\\(\\)]","");
        List<String> exprNums= List.of(expr.split("[\\+\\-\\*\\/]"));
        List<Integer> gameNums=existingGame.getNumbers();
        List<Integer> gameNumsCopy=new ArrayList<>();
        for(Integer i:gameNums){
            gameNumsCopy.add(i);
        }
        gameNumsCopy.remove(0);
        for(String num:exprNums){
            try {
                if(!gameNumsCopy.contains(Integer.parseInt(num))){
                    return false;
                }
            }catch (Exception e){
                return false;
            }

            gameNumsCopy.remove((Object) Integer.parseInt(num));
        }
        return true;
    }

    @Override
    public Integer eval(String expr) throws ScriptException {
        Expression e=new ExpressionBuilder(expr).build();
        return (int) e.evaluate();
    }

    @Override
    public Integer userSolutionDiff(String expression, Principal principal) throws Exception {
        Game game= gameService.getOnePlayerGame(principal);
        MojBrojGame MBgame=game.getGames().getMojBrojGame();
        Long gameId=MBgame.getId();
        if(!game.getIsActive(principal).isActiveMojBroj()){
            throw new Exception("You can submit only once");
        }
        if(!validateExpression(expression,gameId)){
            throw new Exception("Bad expression");
        }
        game.getIsActive(principal).setActiveMojBroj(false);
        mojBrojRepository.save(MBgame);
        int target=MBgame.getNumbers().get(0);
        return Math.abs(eval(expression)-target);
    }

    @Override
    public String getSolution(Principal principal) throws Exception {
        Game game=gameService.getOnePlayerGame(principal);
        MojBrojGame MBgame=game.getGames().getMojBrojGame();
        return MBgame.getSolution();
    }

    @Override
    public MojBrojSubmitResponse submit(MojBrojSubmitRequest request, Principal principal){
        int diff;
        try {
            diff = userSolutionDiff(request.getExpression(), principal);
        } catch (Exception e) {
            return new MojBrojSubmitResponse(e.getMessage(), "", 0, 0);
        }

        Integer numOfPoints=0;
        switch (diff){
            case 0:
                numOfPoints= 30;
                break;
            case 1:
                numOfPoints= 20;
                break;
            case 2:
                numOfPoints=10;
                break;
        }
        String solution = null;
        Game game=null;
        String msg = "";
        int result = 0;
        try {
            solution = getSolution(principal);
            game=gameService.getOnePlayerGame(principal);
            result = eval(request.getExpression());
        } catch (Exception e) {
            new MojBrojSubmitResponse("Something went wrong", solution, numOfPoints, result);
        }
        game.getPoints(principal).setNumOfPointsMojBroj(numOfPoints);
        if(game.getClass()==OnePlayerGame.class){
            onePlayerGameRepository.save((OnePlayerGame) game);
        }
        if(game.getClass()== TwoPlayerGame.class){
            twoPlayerGameRepository.save((TwoPlayerGame) game);
        }

        return new MojBrojSubmitResponse(msg, solution, numOfPoints, result);
    }
}
