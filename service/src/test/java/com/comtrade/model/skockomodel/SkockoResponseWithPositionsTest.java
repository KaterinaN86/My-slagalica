package com.comtrade.model.skockomodel;

import com.comtrade.model.skockomodel.SkockoResponseWithPositions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SkockoResponseWithPositionsTest {

    SkockoResponseWithPositions skockoResponseWithPositions;

    @BeforeEach
    void setUp() {
        skockoResponseWithPositions=new SkockoResponseWithPositions();
    }

    @Test
    void getGoodPositions() {
        skockoResponseWithPositions.setGoodPositions(1);
        Assertions.assertEquals(1, skockoResponseWithPositions.getGoodPositions());
    }

    @Test
    void getBadPositions() {
        skockoResponseWithPositions.setBadPositions(3);
        Assertions.assertEquals(3, skockoResponseWithPositions.getBadPositions());
    }
    @Test
    void constructor() {
        skockoResponseWithPositions=new SkockoResponseWithPositions(false, 1, 2);
        Assertions.assertFalse(skockoResponseWithPositions.isWinning());
        Assertions.assertEquals(1, skockoResponseWithPositions.getGoodPositions());
        Assertions.assertEquals(2, skockoResponseWithPositions.getBadPositions());
    }
}