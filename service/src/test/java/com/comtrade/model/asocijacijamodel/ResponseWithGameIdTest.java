package com.comtrade.model.asocijacijamodel;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ErrorCollector;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ResponseWithGameIdTest {

    ResponseWithGameId responseWithGameId;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        responseWithGameId = new ResponseWithGameId(1L);
    }

    @Test
    public void getGameId() {
        Long id = 1L;
        responseWithGameId.setGameId(1L);
        Assertions.assertEquals(id, responseWithGameId.getGameId());
    }

    @Test
    public void setGameId() {
        Long expected = 1L;
        try {
            Assert.assertEquals(expected, responseWithGameId.getGameId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        responseWithGameId.setGameId(1L);
        Long actual = 1L;
        expected = responseWithGameId.getGameId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }
}