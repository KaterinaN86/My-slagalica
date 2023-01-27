package com.comtrade.model.mojbrojmodel;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ErrorCollector;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.testng.Assert;

import java.util.*;

@ExtendWith(SpringExtension.class)
public class MojBrojSubmitRequestTest {

    MojBrojSubmitRequest mojBrojSubmitRequest;

    public final ErrorCollector ec = new ErrorCollector();


    @Before
    public void setUp() throws Exception {
        mojBrojSubmitRequest = new MojBrojSubmitRequest();
    }

    @Test
    public void getGameId() {
        Long expectedId = 1L;
        mojBrojSubmitRequest.setGameId(1L);
        Assert.assertEquals( expectedId, mojBrojSubmitRequest.getGameId());
    }

    @Test
    public void getExpression() {
        mojBrojSubmitRequest.setExpression("Expression");
        Assert.assertEquals("Expression", mojBrojSubmitRequest.getExpression());
    }

    @Test
    public void setGameId() {
        Long expected = 1L;
        try {
            Assert.assertNotEquals(expected, mojBrojSubmitRequest.getGameId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        mojBrojSubmitRequest.setGameId(1L);
        Long actual = 1L;
        expected = mojBrojSubmitRequest.getGameId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setExpression() {
        String expression = "";
        try {
            Assert.assertNotNull(expression, mojBrojSubmitRequest.getExpression());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        mojBrojSubmitRequest.setExpression("");
        String actual = "";
        expression = mojBrojSubmitRequest.getExpression();
        try {
            Assert.assertEquals(actual, expression);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void testToString() {
        String expected = "";
        Assert.assertNotEquals(expected, mojBrojSubmitRequest.toString());
    }

    @Test
    public void constructorTest() {
        mojBrojSubmitRequest.setGameId(1L);
        String expression = "";
        mojBrojSubmitRequest.expression = "";
        mojBrojSubmitRequest = new MojBrojSubmitRequest(1L, "");

        org.junit.Assert.assertEquals(1L, mojBrojSubmitRequest.getGameId());
        org.junit.Assert.assertEquals(expression, mojBrojSubmitRequest.getExpression());
        org.junit.Assert.assertNotNull(mojBrojSubmitRequest);
    }
}