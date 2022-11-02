package com.comtrade.backend.services;


import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class MojBrojService {
    private int target;
    private LinkedList<Integer> nums;
    public MojBrojService(){
        target =(int)(Math.random()*1000);
        nums=new LinkedList<>();
        for(int i=0;i<4;i++){
            nums.add((int)(Math.random()*9)+1);
        }
        nums.add((int)(Math.random()*3)*5+10);
        nums.add((int)(Math.random()*4)*25+25);
    }
    public MojBrojService(int target,LinkedList<Integer> nums){
        this.target=target;
        this.nums=nums;
    }

    public LinkedList<Integer> getNums() {
        return nums;
    }

    public int getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "mojBrojService{" +
                "target=" + target +
                ", nums=" + nums +
                '}';
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
        if(!exp.contains("+")&& !exp.contains("*")&&!exp.contains("/")&&!exp.contains("-")){
            return exp;
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
    int userSolutionDif(String exp){
        return Math.abs(Integer.parseInt(eval(exp))-target);
    }

}
