package com.comtrade.model.mojbrojmodel;

import com.beust.ah.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;
import java.util.NoSuchElementException;
import java.util.Optional;


@RunWith(SpringRunner.class)
class MojBrojSubmitResponseTest {

    MojBrojSubmitResponse mojBrojSubmitResponse;

    public final ErrorCollector ec = new ErrorCollector();

    @BeforeEach
    void setUp() {
        mojBrojSubmitResponse = new MojBrojSubmitResponse();
    }

    @Test
    void getMsg() {
        mojBrojSubmitResponse.setMsg("Message");
        Assertions.assertEquals("Message", mojBrojSubmitResponse.getMsg());

    }

    @Test
    void getSolution() {
        mojBrojSubmitResponse.setSolution("Solution");
        Assertions.assertEquals("Solution", mojBrojSubmitResponse.getSolution());
    }

    @Test
    void getNumOfPoints() {
        mojBrojSubmitResponse.setNumOfPoints(15);
        Assertions.assertEquals(15, mojBrojSubmitResponse.getNumOfPoints());
    }

    @Test
    void getResult() {
        mojBrojSubmitResponse.setResult(20);
        Assertions.assertEquals(20, mojBrojSubmitResponse.getResult());
    }

    @Test
    void setMsg() {
        String msg = "";
        try {
            Assert.assertNotNull(msg, mojBrojSubmitResponse.getMsg());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        mojBrojSubmitResponse.setMsg("");
        String actual = "";
        msg = mojBrojSubmitResponse.getMsg();
        try {
            Assert.assertEquals(actual, msg);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }

    }

    @Test
    void setSolution() {
        String solution = "";
        try {
            Assert.assertNotNull(solution, mojBrojSubmitResponse.getSolution());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        mojBrojSubmitResponse.setSolution("");
        String actual = "";
        solution = mojBrojSubmitResponse.getMsg();
        try {
            Assert.assertNotEquals(actual, solution);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    void setNumOfPoints() {
        Integer numOfPoints = null;
        try {
            Assert.assertEquals(numOfPoints, mojBrojSubmitResponse.getNumOfPoints());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        mojBrojSubmitResponse.setNumOfPoints(20);
        Integer actual = 5;
        numOfPoints = mojBrojSubmitResponse.getNumOfPoints();
        try {
            Assert.assertNotEquals(actual, numOfPoints);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    void setResult() {
        Integer result = null;
        try {
            Assert.assertEquals(result, mojBrojSubmitResponse.getResult());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        mojBrojSubmitResponse.setResult(20);
        Integer actual = 5;
        result = mojBrojSubmitResponse.getNumOfPoints();
        try {
            Assert.assertNotEquals(actual, result);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    void testToString() {
        String expected = "";
        Assert.assertNotEquals(expected, mojBrojSubmitResponse.toString());
    }

    @Test
    public void constructorTest() {
        String msg = "";
        mojBrojSubmitResponse.setMsg("");
        String solution = "";
        mojBrojSubmitResponse.setSolution("");
        Integer numOfPoints = 20;
        mojBrojSubmitResponse.setNumOfPoints(20);
        Integer result = 20;
        mojBrojSubmitResponse.setResult(20);
        mojBrojSubmitResponse = new MojBrojSubmitResponse("", "", 20, 20);

        Assert.assertEquals(msg, mojBrojSubmitResponse.getMsg());
        Assert.assertEquals(solution, mojBrojSubmitResponse.getSolution());
        Assert.assertEquals(numOfPoints, mojBrojSubmitResponse.getNumOfPoints());
        Assert.assertEquals(result, mojBrojSubmitResponse.getResult());
        org.junit.Assert.assertNotNull(mojBrojSubmitResponse);
    }
}