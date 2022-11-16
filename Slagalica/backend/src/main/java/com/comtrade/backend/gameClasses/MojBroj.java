package com.comtrade.backend.gameClasses;

import java.util.LinkedList;

public class MojBroj {
    private int target;
    private LinkedList<Integer> nums;
    public MojBroj(){
        target =(int)(Math.random()*1000);
        nums=new LinkedList<>();
        for(int i=0;i<4;i++){
            nums.add((int)(Math.random()*9)+1);
        }
        nums.add((int)(Math.random()*3)*5+10);
        nums.add((int)(Math.random()*4)*25+25);
    }
    public MojBroj(int target,LinkedList<Integer> nums){
        this.target=target;
        this.nums=nums;
    }

    @Override
    public String toString() {
        return "MojBroj{" +
                "target=" + target +
                ", nums=" + nums +
                '}';
    }

    public LinkedList<Integer> getNums() {
        return nums;
    }

    public int getTarget() {
        return target;
    }
}
