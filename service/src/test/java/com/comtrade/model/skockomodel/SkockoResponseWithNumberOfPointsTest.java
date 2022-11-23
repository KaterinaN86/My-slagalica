package com.comtrade.model.skockomodel;

import com.comtrade.model.skockomodel.SkockoResponseWithNumberOfPoints;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkockoResponseWithNumberOfPointsTest {

    SkockoResponseWithNumberOfPoints skockoResponseWithNumberOfPoints;
    @BeforeEach
    void setUp() {
        skockoResponseWithNumberOfPoints=new SkockoResponseWithNumberOfPoints();
    }

    @Test
    void getPoints() {
        skockoResponseWithNumberOfPoints.setPoints(30);
        Assertions.assertEquals(30, skockoResponseWithNumberOfPoints.getPoints());
    }

    @Test
    void getCombination() {
        List<Integer> list=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        skockoResponseWithNumberOfPoints.setCombination(list);
        Assertions.assertEquals(list, skockoResponseWithNumberOfPoints.getCombination());
    }

    @Test
    void constructorTest(){
        List<Integer> list=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        skockoResponseWithNumberOfPoints=new SkockoResponseWithNumberOfPoints(true,30, list);
        Assertions.assertTrue(skockoResponseWithNumberOfPoints.isWinning());
        Assertions.assertEquals(30, skockoResponseWithNumberOfPoints.getPoints());
        Assertions.assertEquals(list, skockoResponseWithNumberOfPoints.getCombination());
    }
}