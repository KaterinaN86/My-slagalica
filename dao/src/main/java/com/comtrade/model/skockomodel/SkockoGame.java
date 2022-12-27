package com.comtrade.model.skockomodel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@ToString
@Getter
@Setter
public class SkockoGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Integer> combination = new ArrayList<>();

    public SkockoGame() {
        generateNewCombination();
    }

    public SkockoGame(Long id, List<Integer> combination) {
        this.id = id;
        this.combination = combination;
        generateNewCombination();
    }

    private void generateNewCombination(){
        for (int i=0; i<4; i++){
            int random = (int)(Math.random()*6);
            combination.add(random);
        }
    }
}
