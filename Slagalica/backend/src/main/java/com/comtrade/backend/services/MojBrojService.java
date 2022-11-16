package com.comtrade.backend.services;


import com.comtrade.backend.gameClasses.MojBroj;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class MojBrojService {

    MojBrojService(){}
    public MojBroj generateGame(){
        return new MojBroj();
    }

    boolean validateBracket(String expression){
        int cnt=0;
        int l=expression.length();
        for (int i=0;i<l;i++){
            if(expression.charAt(i)=='('){
                cnt++;
            } else if (expression.charAt(i)==')') {
                cnt--;
            }
            if(cnt<0){
                return false;
            }
        }
        if (cnt!=0){
            return false;
        }
        return true;
    }
    String eval(String exp){//deljenje zaokruzuje na donji Integer (round)
        if(exp.startsWith("(")&&exp.endsWith(")")){
            return eval(exp.substring(1,exp.length()-1));
        }
        if (exp.contains("(")){
            exp=exp.replace(exp.substring(exp.indexOf("("),exp.lastIndexOf(")")+1),eval(exp.substring(exp.indexOf("("),exp.lastIndexOf(")")+1)));
        }
        if(exp.contains("+")){
            return String.valueOf(Integer.parseInt(eval(exp.substring(0,exp.indexOf("+"))))+Integer.parseInt(eval(exp.substring(exp.indexOf("+")+1))));
        }
        if(exp.contains("-")){
            return String.valueOf(Integer.parseInt(eval(exp.substring(0,exp.indexOf("-"))))-Integer.parseInt(eval(exp.substring(exp.indexOf("-")+1))));
        }
        if(exp.contains("/")){
            return String.valueOf(Integer.parseInt(eval(exp.substring(0,exp.indexOf("/"))))/Integer.parseInt(eval(exp.substring(exp.indexOf("/")+1))));
        }
        if(exp.contains("*")){
            return String.valueOf(Integer.parseInt(eval(exp.substring(0,exp.indexOf("*"))))*Integer.parseInt(eval(exp.substring(exp.indexOf("*")+1))));
        }
        return exp;
    }
    int userSolutionDif(String exp,int target){
        return Math.abs(Integer.parseInt(eval(exp))-target);
    }

}
