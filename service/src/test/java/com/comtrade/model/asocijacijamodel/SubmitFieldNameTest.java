package com.comtrade.model.asocijacijamodel;

import org.testng.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;


@RunWith(SpringRunner.class)
public class SubmitFieldNameTest {

    SubmitFieldName submitFieldName;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        submitFieldName = new SubmitFieldName();
    }

    @Test
    public void getGameId() {
        Long id = 1L;
        submitFieldName.setGameId(1L);
        Assert.assertEquals(id, submitFieldName.getGameId());
    }

    @Test
    public void getFieldName() {
        submitFieldName.setFieldName("");
        String fieldName="";
        Assert.assertEquals(fieldName, submitFieldName.getFieldName());

    }

    @Test
    public void setGameId() {
        Long expected = 1L;
        try {
            Assert.assertNotEquals(expected, submitFieldName.getGameId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        submitFieldName.setGameId(1L);
        Long actual = 1L;
        expected = submitFieldName.getGameId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setFieldName() {
        String fieldName = "";
        try {
            Assert.assertNotEquals(fieldName, submitFieldName.getFieldName());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        submitFieldName.setFieldName("");
        String actual = "";
        fieldName = submitFieldName.getFieldName();
        try {
            Assert.assertEquals(actual, fieldName);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }
}