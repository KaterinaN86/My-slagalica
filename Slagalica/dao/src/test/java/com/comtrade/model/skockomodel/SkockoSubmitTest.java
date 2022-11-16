package com.comtrade.model.skockomodel;

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
        assertEquals(1L, skockoSubmit.getGameId());
    }

    @Test
    void getCombination() {
        List<Integer> list=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        skockoSubmit.setCombination(list);
        assertEquals(list, skockoSubmit.getCombination());
    }

    @Test
    void getAttempt() {
        skockoSubmit.setAttempt(5);
        assertEquals(5, skockoSubmit.getAttempt());
    }

    @Test
    void allArgsConstructorTest(){
        List<Integer> list=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        skockoSubmit=new SkockoSubmit(1L, list, 5);
        assertEquals(1L, skockoSubmit.getGameId());
        assertEquals(list, skockoSubmit.getCombination());
        assertEquals(5, skockoSubmit.getAttempt());
    }
}