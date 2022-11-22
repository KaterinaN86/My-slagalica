package com.comtrade.model.skockomodel;

import com.comtrade.model.skockomodel.SkockoGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkockoGameTest {

    SkockoGame skockoGame;
    @BeforeEach
    void setUp(){
        skockoGame=new SkockoGame();
    }

    @Test
    void getId() {
        skockoGame.setId(1L);
        Assertions.assertEquals(1L, skockoGame.getId());
    }

    @Test
    void getCombination() {
        List<Integer> list=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        skockoGame.setCombination(list);
        Assertions.assertEquals(list, skockoGame.getCombination());
    }

    @Test
    void isActive() {
        skockoGame.setActive(false);
        Assertions.assertFalse(skockoGame.isActive());
    }

    @Test
    void constructorTest(){
        skockoGame=null;
        List<Integer> list=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        skockoGame=new SkockoGame(123L,list, true);
        Assertions.assertEquals(123L, skockoGame.getId());
        Assertions.assertEquals(list, skockoGame.getCombination());
        Assertions.assertTrue(skockoGame.isActive());
    }
}