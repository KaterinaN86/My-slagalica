package com.comtrade.model.asocijacijamodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;


@RunWith(SpringRunner.class)
public class ResponseWithFieldValueTest {

    ResponseWithFieldValue responseWithFieldValue;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        responseWithFieldValue = new ResponseWithFieldValue("Correct.");
    }

    @Test
    public void getFieldValue() {
        String fieldValue = "";
        responseWithFieldValue.setFieldValue("");
        Assert.assertEquals(fieldValue, responseWithFieldValue.getFieldValue());
    }

    @Test
    public void setFieldValue() {
        String fieldValue = "";
        try {
            Assert.assertNotEquals(fieldValue, responseWithFieldValue.getFieldValue());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        responseWithFieldValue.setFieldValue("");
        String actual = "";
        fieldValue = responseWithFieldValue.getFieldValue();
        try {
            Assert.assertEquals(actual, fieldValue);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);

        }
    }
}