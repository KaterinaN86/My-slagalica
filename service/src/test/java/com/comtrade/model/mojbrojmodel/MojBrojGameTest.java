package com.comtrade.model.mojbrojmodel;

import org.testng.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
class MojBrojGameTest {

    MojBrojGame mojBrojGame;

    public final ErrorCollector ec = new ErrorCollector();

    @BeforeEach
    public void setUp() {
        mojBrojGame = new MojBrojGame();
    }

    @Test
    public void createSolutionFromNums() {
       mojBrojGame.setSolution(("(3+5)*8"));
       String expected = "(3+5)*8";
       Assert.assertEquals(expected, mojBrojGame.getSolution());
    }

    @Test
    public void getId() {
        Long id = 1L;
        mojBrojGame.setId(1L);
        Assert.assertEquals(id, mojBrojGame.getId());
    }

    @Test
    public void getNumbers() {
      List<Integer> list= new ArrayList<>(Arrays.asList(1,4,6,2,10,100));
      Assert.assertNotEquals(list, mojBrojGame.getNumbers());

    }

    @Test
    public void getSolution() {
        mojBrojGame.setSolution("Solution");
        Assert.assertEquals("Solution", mojBrojGame.getSolution());

    }

    @Test
    public void setId() {
        Long expected = 1L;
        try {
            Assert.assertNotEquals(expected, mojBrojGame.getId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        mojBrojGame.setId(1L);
        Long actual = 1L;
        expected = mojBrojGame.getId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setNumbers() {
        List<Integer> numbers = new ArrayList<>();
        try {
           Assert.assertNotEquals(numbers, mojBrojGame.getNumbers());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        List<Integer> actualNumbers = new ArrayList<>(Arrays.asList(2,4,1,8,15,75));
        try {
            org.testng.Assert.assertNotEquals(actualNumbers, mojBrojGame.getNumbers());
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }

    }

    @Test
    public void setSolution() {
        String result = "";
        try {
           Assert.assertNotEquals(result, mojBrojGame.getSolution());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        mojBrojGame.setSolution("");
        String actual = "5";
        result = mojBrojGame.getSolution();
        try {
            Assert.assertNotEquals(actual, result);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void testToString() {
        String expected = "";
        Assert.assertNotEquals(expected, mojBrojGame.toString());
    }

    /*@Test
    public void constructorTest() {
        Long id = 1L;
        List<Integer> number = new ArrayList<>(Arrays.asList(1,4,2,8,10,50));
        String solution = "";
        mojBrojGame = new MojBrojGame(1L, new ArrayList<>(Arrays.asList(1,4,2,8,10,50)), true, "");

        Assert.assertEquals(id, mojBrojGame.getId());
        Assert.assertEquals(number, mojBrojGame.getNumbers());
        Assert.assertEquals(solution, mojBrojGame.getSolution());
        Assert.assertNotNull(mojBrojGame);
    }*/
}