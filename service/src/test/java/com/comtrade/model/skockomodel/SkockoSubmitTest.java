package com.comtrade.model.skockomodel;

import com.comtrade.model.skockomodel.SkockoSubmit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SkockoSubmitTest {

    SkockoSubmit skockoSubmit;

    @BeforeEach
    void setUp() {
        skockoSubmit=new SkockoSubmit();
    }

    @Test
    void getGameId() {
        skockoSubmit.setGameId(1L);
        Assertions.assertEquals(1L, skockoSubmit.getGameId());
    }

    @Test
    void getCombination() {
        List<Integer> list=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        skockoSubmit.setCombination(list);
        Assertions.assertEquals(list, skockoSubmit.getCombination());
    }

    @Test
    void getAttempt() {
        skockoSubmit.setAttempt(5);
        Assertions.assertEquals(5, skockoSubmit.getAttempt());
    }

    @Test
    void allArgsConstructorTest(){
        List<Integer> list=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        skockoSubmit=new SkockoSubmit(1L, list, 5);
        Assertions.assertEquals(1L, skockoSubmit.getGameId());
        Assertions.assertEquals(list, skockoSubmit.getCombination());
        Assertions.assertEquals(5, skockoSubmit.getAttempt());
    }
}