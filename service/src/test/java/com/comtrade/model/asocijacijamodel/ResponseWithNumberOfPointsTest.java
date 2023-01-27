package com.comtrade.model.asocijacijamodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ResponseWithNumberOfPointsTest {

    ResponseWithNumberOfPoints responseWithNumberOfPoints;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        responseWithNumberOfPoints = new ResponseWithNumberOfPoints();
    }

    @Test
    public void getPoints() {
        Double points = 21.0;
        responseWithNumberOfPoints.setPoints(21.0);
        Assert.assertEquals(points, responseWithNumberOfPoints.getPoints());
    }

    @Test
    public void setPoints() {
        Double points = null;
        try {
            org.testng.Assert.assertEquals(points, responseWithNumberOfPoints.getPoints());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        responseWithNumberOfPoints.setPoints(21.0);
        Double actual = 21.0;
        points = responseWithNumberOfPoints.getPoints();
        try {
            org.testng.Assert.assertEquals(actual, points);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void constructorTest() {
        Double points = 21.0;
        responseWithNumberOfPoints = new ResponseWithNumberOfPoints(21.0);
        Assert.assertEquals(points, responseWithNumberOfPoints.getPoints());
    }
}