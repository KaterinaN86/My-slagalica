package com.comtrade.model.mojbrojmodel;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Entity
@ToString
@Getter
@Setter
public class MojBrojGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Integer> numbers=new ArrayList<>();
    private boolean isActive;
    private String solution;

    public MojBrojGame() {
        isActive=true;
        initializeRandom();
    }

    public MojBrojGame(Long id, ArrayList<Integer> numbers, boolean isActive, String solution) {
        this.id = id;
        this.numbers = numbers;
        this.isActive = isActive;
        this.solution=solution;
    }
    public void initializeRandom(){
        numbers.clear();
        numbers.add((int)(Math.random()*1000));
        for(int i=0;i<4;i++){
            numbers.add((int)(Math.random()*9)+1);
        }
        numbers.add((int)(Math.random()*3)*5+10);
        numbers.add((int)(Math.random()*4)*25+25);
        solution="no solution";

        int numOfUsedNums=(int)(Math.random()*4+3);
        ArrayList<Integer> numsToUse=new ArrayList<>();
        ArrayList<Integer> usedIndexes=new ArrayList<>();
        for(int i=0;i<numOfUsedNums;i++){
            int randomIndex = (int) (Math.random() *6+1);
            while (usedIndexes.contains(randomIndex)) {
                randomIndex = (int) (Math.random() *6+1);
            }
            numsToUse.add(numbers.get(randomIndex));
            usedIndexes.add(randomIndex);
        }
        solution=createSolutionFromNums(numsToUse);
    }
    String createSolutionFromNums(ArrayList<Integer> nums){
        String expression="";
        HashMap<Integer,String> op=new HashMap<>();
        op.put(0,"*");
        op.put(4,"*");
        op.put(1,"+");
        op.put(2,"-");
        op.put(3,"/");
        for(Integer num:nums){
            expression+=num.toString()+op.get((int)(Math.random()*5));
        }
        expression=expression.substring(0,expression.length()-1);
        return expression;//todo create brackets
    }
}
