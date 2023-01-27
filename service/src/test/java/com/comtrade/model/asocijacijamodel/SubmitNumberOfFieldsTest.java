package com.comtrade.model.asocijacijamodel;

import org.testng.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;


@RunWith(SpringRunner.class)
public class SubmitNumberOfFieldsTest {

    SubmitNumberOfFields submitNumberOfFields;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        submitNumberOfFields = new SubmitNumberOfFields();
    }

    @Test
    public void getGameId() {
        Long id = 1L;
        submitNumberOfFields.setGameId(1L);
        Assertions.assertEquals(id, submitNumberOfFields.getGameId());
    }

    @Test
    public void getNumberOfOpenedFields() {
        Double openFields = 3.0;
        submitNumberOfFields.setNumberOfOpenedFields(3.0);
        Assert.assertEquals(openFields, submitNumberOfFields.getNumberOfOpenedFields());
    }

    @Test
    public void setGameId() {
        Long expect = 1L;
        try {
            Assert.assertNotEquals(expect, submitNumberOfFields.getGameId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        submitNumberOfFields.setGameId(1L);
        Long actual = 1L;
        expect = submitNumberOfFields.getGameId();
        try {
            Assert.assertEquals(actual, expect);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setNumberOfOpenedFields() {
        Double openFields = null;
        try {
            org.testng.Assert.assertEquals(openFields, submitNumberOfFields.getNumberOfOpenedFields());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        submitNumberOfFields.setNumberOfOpenedFields(21.0);
        Double actual = 21.0;
        openFields = submitNumberOfFields.getNumberOfOpenedFields();
        try {
            org.testng.Assert.assertEquals(actual, openFields);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }
    @Test
    public void contructorTest() {
        Long id = 1L;
        Double numberOfPoints = 26.0;
        submitNumberOfFields = new SubmitNumberOfFields(1L, 26.0);
        Assert.assertEquals(id, submitNumberOfFields.getGameId());
        Assert.assertEquals(numberOfPoints, submitNumberOfFields.getNumberOfOpenedFields());
    }
}