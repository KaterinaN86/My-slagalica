package com.comtrade.model.asocijacijamodel;

import org.testng.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.directory.NoSuchAttributeException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class SubmitFieldValueTest {

    SubmitFieldValue submitFieldValue;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        submitFieldValue = new SubmitFieldValue(1L, "", "");
    }

    @Test
    public void getWord() {
        String word = "AVION";
        submitFieldValue.setWord("AVION");
        Assert.assertEquals(word, submitFieldValue.getWord());

    }

    @Test
    public void setWord() {
        String word = "VINO";
        try {
            Assert.assertNotEquals(word, submitFieldValue.getWord());
        } catch (Exception e) {
            ec.addError(e);
        }
        submitFieldValue.setWord("AVION");
        String actual = "KRILo";
        try {
            Assert.assertNotEquals(actual, submitFieldValue.getWord());
        } catch (Exception e) {
            ec.addError(e);
        }
    }
}