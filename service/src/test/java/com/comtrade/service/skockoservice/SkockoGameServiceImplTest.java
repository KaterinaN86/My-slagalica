package com.comtrade.service.skockoservice;//package com.comtrade.service.skockoservice;

import com.comtrade.model.skockomodel.SkockoGame;
import com.comtrade.repository.skockorepository.SkockoGameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkockoGameServiceImplTest {

    @Mock
    SkockoGameRepository skockoGameRepository;
    SkockoGameService skockoGameService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        skockoGameService=new SkockoGameServiceImpl(skockoGameRepository);
    }

    @Test
    void createNewGame() throws NoSuchAlgorithmException {
        SkockoGame skockoGame=skockoGameService.createNewGame();
        Assertions.assertFalse(skockoGame.isActive());
    }

    @Test
    void isWinningCombination() {
        List<Integer> winningCombination=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        List<Integer> combination=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        assertTrue(skockoGameService.isWinningCombination(winningCombination, combination));
        combination=new ArrayList<>(Arrays.asList(1, 2, 2, 1));
        assertFalse(skockoGameService.isWinningCombination(winningCombination, combination));
    }

    @Test
    void getNumberOfCorrectlyPlacedSymbolsInCombination() {
        List<Integer> winningCombination=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        List<Integer> combination=new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        assertEquals(4, skockoGameService.getNumberOfCorrectlyPlacedSymbolsInCombination(winningCombination, combination));
        combination=new ArrayList<>(Arrays.asList(1, 2, 3, 1));
        assertEquals(3, skockoGameService.getNumberOfCorrectlyPlacedSymbolsInCombination(winningCombination, combination));
        combination=new ArrayList<>(Arrays.asList(1, 1, 1, 1));
        assertEquals(1, skockoGameService.getNumberOfCorrectlyPlacedSymbolsInCombination(winningCombination, combination));
        combination=new ArrayList<>(Arrays.asList(2, 1, 4, 3));
        assertEquals(0, skockoGameService.getNumberOfCorrectlyPlacedSymbolsInCombination(winningCombination, combination));
    }

    @Test
    void getNumberOfMisplacedSymbolsInCombination() {
        assertEquals(0, skockoGameService.getNumberOfMisplacedSymbolsInCombination(new ArrayList<>(Arrays.asList(1, 2, 3, 4)), new ArrayList<>(Arrays.asList(1, 2, 3, 4))));
        assertEquals(0, skockoGameService.getNumberOfMisplacedSymbolsInCombination(new ArrayList<>(Arrays.asList(1, 2, 3, 4)), new ArrayList<>(Arrays.asList(1, 1, 1, 1))));
        assertEquals(4, skockoGameService.getNumberOfMisplacedSymbolsInCombination(new ArrayList<>(Arrays.asList(1, 2, 3, 4)), new ArrayList<>(Arrays.asList(2, 1, 4, 3))));
        assertEquals(0, skockoGameService.getNumberOfMisplacedSymbolsInCombination(new ArrayList<>(Arrays.asList(1, 2, 3, 4)), new ArrayList<>(Arrays.asList(1, 1, 3, 3))));
        assertEquals(2, skockoGameService.getNumberOfMisplacedSymbolsInCombination(new ArrayList<>(Arrays.asList(1, 2, 3, 4)), new ArrayList<>(Arrays.asList(2, 1, 5, 4))));
    }

    @Test
    void numberOfPoints() {
        assertEquals(20, skockoGameService.numberOfPoints(5));
        assertEquals(30, skockoGameService.numberOfPoints(4));
        assertEquals(30, skockoGameService.numberOfPoints(0));
        assertEquals(0, skockoGameService.numberOfPoints(8));
    }
}