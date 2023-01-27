package com.comtrade.model.spojnicemodel;

import org.testng.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ErrorCollector;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class SpojniceGameTest {

    SpojniceGame spojniceGame;
    PairsModel pairsModel;
    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        spojniceGame = new SpojniceGame();
    }

    @Test
    public void getId() {
        Long id = 1L;
        spojniceGame.setId(1L);
        Assert.assertEquals(id, spojniceGame.getId());
    }

    @Test
    public void setId() {
        Long expected = 1L;
        try {
            Assert.assertNotEquals(expected, spojniceGame.getId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        spojniceGame.setId(1L);
        Long actual = 1L;
        expected = spojniceGame.getId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void getPairsModel() {
    pairsModel = new PairsModel(1L, "'SPOJITE PRONALAZAČE SA NJIHOVIM IZUMIMA'",
            "'1:INDUKCIONA MAŠINA, 2:PENICILIN, 3:ELEKTRONSKA POŠTA, 4: NUKLEARNI REAKTOR, 5:GROMOBRAN, 6:ŽIROSKOP, 7:TRAKTOR, 8:RADIO TALASI'",
            "'4:ENRIKO FERMI, 6:LEON FUKO, 8:HAJNRIH HERC, 1:NIKOLA TESLA, 3:REJ TOMPLINSON, 7: BENDŽAMIN HOLT, 2: ALEKSANDAR FLEMING, 5:BENDŽAMIN FRENKLIN'");
    Assert.assertNotNull(pairsModel);

    }

    @Test
    public void setPairsModel() {
        pairsModel = new PairsModel(1L, "'SPOJITE PRONALAZAČE SA NJIHOVIM IZUMIMA'",
                "'1:INDUKCIONA MAŠINA, 2:PENICILIN, 3:ELEKTRONSKA POŠTA, 4: NUKLEARNI REAKTOR, 5:GROMOBRAN, 6:ŽIROSKOP, 7:TRAKTOR, 8:RADIO TALASI'",
                "'4:ENRIKO FERMI, 6:LEON FUKO, 8:HAJNRIH HERC, 1:NIKOLA TESLA, 3:REJ TOMPLINSON, 7: BENDŽAMIN HOLT, 2: ALEKSANDAR FLEMING, 5:BENDŽAMIN FRENKLIN'");
        try {
            Assert.assertNotNull(pairsModel);
        }
        catch (Exception e) {
            ec.addError(e);
        }
        try {
            Assert.assertNotNull(pairsModel);
        } catch (Exception e) {
            ec.addError(e);
        }

    }
    @Test
    public void contructorTest() {
        Long id = 1L;
        pairsModel = new PairsModel(1L,
                "'SPOJITE PRONALAZAČE SA NJIHOVIM IZUMIMA'",
                "'1:INDUKCIONA MAŠINA, 2:PENICILIN, 3:ELEKTRONSKA POŠTA, 4: NUKLEARNI REAKTOR, 5:GROMOBRAN, 6:ŽIROSKOP, 7:TRAKTOR, 8:RADIO TALASI'",
                "'4:ENRIKO FERMI, 6:LEON FUKO, 8:HAJNRIH HERC, 1:NIKOLA TESLA, 3:REJ TOMPLINSON, 7: BENDŽAMIN HOLT, 2: ALEKSANDAR FLEMING, 5:BENDŽAMIN FRENKLIN'");
       spojniceGame = new SpojniceGame(1L, pairsModel);
        Assert.assertEquals(id, spojniceGame.getId());
        Assert.assertEquals(pairsModel, spojniceGame.getPairsModel());
    }
}