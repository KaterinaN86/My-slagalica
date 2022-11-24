package com.comtrade.service.mojbrojservice;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.repository.mojbrojrepository.MojBrojRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MojBrojServiceImpl implements MojBrojService{
    private final MojBrojRepository mojBrojRepository;

    public MojBrojServiceImpl(MojBrojRepository mojBrojRepository) {
        this.mojBrojRepository = mojBrojRepository;
    }

    @Override
    public MojBrojGame createNewGame() {
        MojBrojGame game=new MojBrojGame();
        mojBrojRepository.save(game);
        return game;
    }

    @Override
    public boolean validateBrackets() {
        return false;
    }

    @Override
    public String evaluate(String expr) {
        if(expr.startsWith("(")&&expr.endsWith(")")){
            return evaluate(expr.substring(1,expr.length()-1));
        }
        if (expr.contains("(")){
            expr=expr.replace(expr.substring(expr.indexOf("("),expr.lastIndexOf(")")+1),evaluate(expr.substring(expr.indexOf("("),expr.lastIndexOf(")")+1)));
        }
        if(expr.contains("+")){
            return String.valueOf(Integer.parseInt(evaluate(expr.substring(0,expr.indexOf("+"))))+Integer.parseInt(evaluate(expr.substring(expr.indexOf("+")+1))));
        }
        if(expr.contains("-")){
            return String.valueOf(Integer.parseInt(evaluate(expr.substring(0,expr.lastIndexOf("-"))))-Integer.parseInt(evaluate(expr.substring(expr.lastIndexOf("-")+1))));
        }
        if(expr.contains("/")){
            return String.valueOf(Integer.parseInt(evaluate(expr.substring(0,expr.indexOf("/"))))/Integer.parseInt(evaluate(expr.substring(expr.indexOf("/")+1))));
        }
        if(expr.contains("*")){
            return String.valueOf(Integer.parseInt(evaluate(expr.substring(0,expr.indexOf("*"))))*Integer.parseInt(evaluate(expr.substring(expr.indexOf("*")+1))));
        }
        return expr;
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
        existingGame.setActive(false);
        mojBrojRepository.save(existingGame);
        int target=existingGame.getNumbers().get(0);
        return Math.abs(Integer.parseInt(evaluate(expression))-target);
    }
}
