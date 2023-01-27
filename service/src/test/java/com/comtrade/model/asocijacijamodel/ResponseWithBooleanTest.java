package com.comtrade.model.asocijacijamodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ResponseWithBooleanTest {

    ResponseWithBoolean responseWithBoolean;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        responseWithBoolean = new ResponseWithBoolean(true);
    }

    @Test
    public void isCorrect() {
        responseWithBoolean.setCorrect(true);
        Assert.assertTrue(responseWithBoolean.isCorrect());
    }

    @Test
    public void setCorrect() {
        try {
            Assert.assertEquals(true, responseWithBoolean.isCorrect());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        responseWithBoolean.setCorrect(true);
        try {
            Assert.assertEquals(true, responseWithBoolean.isCorrect());
        } catch (Exception e) {
            ec.addError(e);
        }
    }
}