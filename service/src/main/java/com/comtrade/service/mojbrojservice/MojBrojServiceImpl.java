package com.comtrade.service.mojbrojservice;


import com.comtrade.exceptions.BadExpressionException;
import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.exceptions.IllegalSubmitException;
import com.comtrade.model.Timers;
import com.comtrade.model.games.Game;
import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitRequest;
import com.comtrade.model.mojbrojmodel.MojBrojSubmitResponse;
import com.comtrade.repository.TimersRepository;
import com.comtrade.repository.mojbrojrepository.MojBrojRepository;
import com.comtrade.responses.Response;
import com.comtrade.service.gameservice.GameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private final TimersRepository timersRepository;


    @Autowired
    private GameServiceImpl gameService;
    public MojBrojServiceImpl(MojBrojRepository mojBrojRepository, TimersRepository timersRepository) {
        this.mojBrojRepository = mojBrojRepository;
        this.timersRepository = timersRepository;
    }

    @Override
    public MojBrojGame getInitData(Principal principal) throws GameNotFoundException {
        Game game = gameService.getGame(principal);
        gameService.saveGame(game);
        Timers timers = game.getTimers(principal);
        timers.setStartTimeMojBroj(LocalTime.now());
        timersRepository.save(timers);
        game.setTimers(principal, timers);
        gameService.saveGame(game);
        return getGame(principal);
    }

    @Override
    public MojBrojGame getGame(Principal principal) throws GameNotFoundException {
        Game game=gameService.getGame(principal);
        if(game.getGames().getMojBrojGame()!=null){
            return game.getGames().getMojBrojGame();
        }else{
            MojBrojGame mojBrojGame=createNewGame();
            game.getGames().setMojBrojGame(mojBrojGame);
            gameService.saveGame(game);
            return mojBrojGame;
        }
    }

    @Override
    public MojBrojGame createNewGame() {
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
    public MojBrojGame createNewGame(Long id, List<Integer> nums, Boolean isActive, String solution){
        MojBrojGame game=new MojBrojGame(id,nums,solution);
        mojBrojRepository.save(game);
        return game;
    }

    @Override
    public boolean validateExpression(String expr, long gameId) {
        Optional<MojBrojGame> game= mojBrojRepository.findById(gameId);
        if(game.isEmpty()){return false;}
        MojBrojGame existingGame=game.get();
        int cnt=countBrackets(expr);
        if (cnt!=0){return false;}
        expr=expr.replaceAll("[\\(\\)]","");
        List<String> exprNums= List.of(expr.split("[\\+\\-\\*\\/]"));
        List<Integer> gameNums=existingGame.getNumbers();
        List<Integer> gameNumsCopy=new ArrayList<>();
        gameNums.stream().forEach(gameNumsCopy::add);
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

    public int countBrackets(String expr){
        int cnt=0;
        for(int i=0;i<expr.length();i++){
            if(expr.charAt(i)=='('){
                cnt++;
            } else if (expr.charAt(i)==')') {
                cnt--;
            }
            if(cnt<0){
                log.warn("A closed parenthesis cannot come before an open parenthesis!");
                return cnt;
            }
        }
        return cnt;
    }


    @Override
    public Integer eval(String expr) throws ScriptException {
        Expression e=new ExpressionBuilder(expr).build();
        return (int) e.evaluate();
    }

    @Override
    public Integer userSolutionDiff(String expression, Principal principal) throws IllegalSubmitException, GameNotFoundException, BadExpressionException, ScriptException {
        Game game= gameService.getGame(principal);
        MojBrojGame mojBrojGame=game.getGames().getMojBrojGame();
        Long gameId=mojBrojGame.getId();
        if(!game.getIsActive(principal).isActiveMojBroj()){
            throw new IllegalSubmitException("You can submit only once");
        }
        if(!validateExpression(expression,gameId)){
            throw new BadExpressionException("Bad expression");
        }
        game.getIsActive(principal).setActiveMojBroj(false);
        mojBrojRepository.save(mojBrojGame);
        int target=mojBrojGame.getNumbers().get(0);
        return Math.abs(eval(expression)-target);
    }

    @Override
    public String getSolution(Principal principal) throws GameNotFoundException {
        Game game=gameService.getGame(principal);
        MojBrojGame mojBrojGame=game.getGames().getMojBrojGame();
        return mojBrojGame.getSolution();
    }

    @Override
    public MojBrojSubmitResponse submit(MojBrojSubmitRequest request, Principal principal){
        int diff;
        try {
            diff = userSolutionDiff(request.getExpression(), principal);
        } catch (Exception e) {
            return new MojBrojSubmitResponse(e.getMessage(), "", 0, 0);
        }

        int numOfPoints=getNumOfPoints(diff);
        String solution = null;
        Game game=null;
        String msg = "";
        int result = 0;
        try {
            solution = getSolution(principal);
            game=gameService.getGame(principal);
            result = eval(request.getExpression());
        } catch (Exception e) {
            return new MojBrojSubmitResponse("Something went wrong", solution, numOfPoints, result);
        }
        game.getPoints(principal).setNumOfPointsMojBroj(numOfPoints);
        gameService.saveGame(game);
        return new MojBrojSubmitResponse(msg, solution, numOfPoints, result);
    }

    public int getNumOfPoints(int diff){
        int numOfPoints=0;
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
            default:
                numOfPoints=0;
        }
        return numOfPoints;
    }

    @Override
    public ResponseEntity<Response> finishGame(Principal principal) throws GameNotFoundException {
        Game game=gameService.getGame(principal);
        MojBrojGame mojBrojGame=game.getGames().getMojBrojGame();
        game.getIsActive(principal).setActiveMojBroj(false);
        gameService.saveGame(game);
        mojBrojRepository.save(mojBrojGame);
        return ResponseEntity.ok().build();
    }
    public boolean isActiveGame(Principal principal) throws GameNotFoundException {
        return gameService.getGame(principal).getIsActive(principal).isActiveMojBroj();
    }
}
