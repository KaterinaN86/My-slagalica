package com.comtrade.model.mojbrojmodel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.ArrayList;
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

    public MojBrojGame() {
        isActive=true;
        initializeRandom();
    }

    public MojBrojGame(Long id, List<Integer> numbers, boolean isActive) {
        this.id = id;
        this.numbers = numbers;
        this.isActive = isActive;
    }
    private void initializeRandom(){
        numbers.add((int)(Math.random()*1000));
        for(int i=0;i<4;i++){
            numbers.add((int)(Math.random()*9)+1);
        }
        numbers.add((int)(Math.random()*3)*5+10);
        numbers.add((int)(Math.random()*4)*25+25);
    }
}
