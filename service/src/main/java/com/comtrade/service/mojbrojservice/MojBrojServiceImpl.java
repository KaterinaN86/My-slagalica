package com.comtrade.service.mojbrojservice;


import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.repository.mojbrojrepository.MojBrojRepository;
import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Service;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class MojBrojServiceImpl implements MojBrojService{
    private final MojBrojRepository mojBrojRepository;

    public MojBrojServiceImpl(MojBrojRepository mojBrojRepository) {
        this.mojBrojRepository = mojBrojRepository;
    }

    @Override
    public MojBrojGame createNewGame(){
        MojBrojGame game=new MojBrojGame();
        List<Integer> nums=game.getNumbers();
        try {
            int target=eval(game.getSolution());
            while(target>999 || target<=0){
                System.out.println(target);
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
    public Integer userSolutionDiff(String expression, long gameId) throws Exception {
        Optional<MojBrojGame> game= mojBrojRepository.findById(gameId);
        if(game.isEmpty()){
            throw new Exception("Wrong game id");
        }
        MojBrojGame existingGame=game.get();
        if(existingGame.isActive()==false){
            throw new Exception("You can submit only once");
        }
        if(!validateExpression(expression,gameId)){
            throw new Exception("Bad expression");
        }
        existingGame.setActive(false);
        mojBrojRepository.save(existingGame);
        int target=existingGame.getNumbers().get(0);
        return Math.abs(eval(expression)-target);
    }

    @Override
    public String getSolution(Long gameid) {
        Optional<MojBrojGame> game=mojBrojRepository.findById(gameid);
        if (game.isEmpty()){return "";}
        return game.get().getSolution();
    }
}
