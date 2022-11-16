package com.comtrade.model.skockomodel;

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
        assertEquals(1, skockoResponseWithPositions.getGoodPositions());
    }

    @Test
    void getBadPositions() {
        skockoResponseWithPositions.setBadPositions(3);
        assertEquals(3, skockoResponseWithPositions.getBadPositions());
    }
    @Test
    void constructor() {
        skockoResponseWithPositions=new SkockoResponseWithPositions(false, 1, 2);
        assertFalse(skockoResponseWithPositions.isWinning());
        assertEquals(1, skockoResponseWithPositions.getGoodPositions());
        assertEquals(2, skockoResponseWithPositions.getBadPositions());
    }
}