package com.comtrade.model.spojnicemodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ErrorCollector;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.NoSuchElementException;

@ExtendWith(SpringExtension.class)
public class PairsModelTest {

    PairsModel pairsModel;

    public final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        pairsModel = new PairsModel();
    }

    @Test
    public void getId() {
        Long id = 1L;
        pairsModel.setId(1L);
        Assert.assertEquals(id, pairsModel.getId());

    }

    @Test
    public void getHeadline() {
        String headLine = "";
        pairsModel.setHeadline("");
        Assert.assertEquals(headLine, pairsModel.getHeadline());

    }

    @Test
    public void getColumn1() {
        String first = "'1:INDUKCIONA MAŠINA, 2:PENICILIN, 3:ELEKTRONSKA POŠTA, 4: NUKLEARNI REAKTOR, 5:GROMOBRAN, 6:ŽIROSKOP, 7:TRAKTOR, 8:RADIO TALASI'";
        pairsModel.setColumn1("'1:INDUKCIONA MAŠINA, 2:PENICILIN, 3:ELEKTRONSKA POŠTA, 4: NUKLEARNI REAKTOR, 5:GROMOBRAN, 6:ŽIROSKOP, 7:TRAKTOR, 8:RADIO TALASI'");
        Assert.assertEquals(first, pairsModel.getColumn1());
    }

    @Test
    public void getColumn2() {
        String second = "'4:ENRIKO FERMI, 6:LEON FUKO, 8:HAJNRIH HERC, 1:NIKOLA TESLA, 3:REJ TOMPLINSON, 7: BENDŽAMIN HOLT, 2: ALEKSANDAR FLEMING, 5:BENDŽAMIN FRENKLIN'";
        pairsModel.setColumn2("'4:ENRIKO FERMI, 6:LEON FUKO, 8:HAJNRIH HERC, 1:NIKOLA TESLA, 3:REJ TOMPLINSON, 7: BENDŽAMIN HOLT, 2: ALEKSANDAR FLEMING, 5:BENDŽAMIN FRENKLIN'");
        Assert.assertEquals(second, pairsModel.getColumn2());
    }

    @Test
    public void setId() {
        Long expected = 1L;
        try {
            Assert.assertNotEquals(expected, pairsModel.getId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        pairsModel.setId(1L);
        Long actual = 1L;
        expected = pairsModel.getId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setHeadline() {
        String headLine = "'SPOJITE PRONALAZAČE SA NJIHOVIM IZUMIMA'";
        try {
            Assert.assertNotEquals(headLine, pairsModel.getHeadline());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        pairsModel.setHeadline("'SPOJITE PRONALAZAČE SA NJIHOVIM IZUMIMA'");
        String actual = "'SPOJITE PRONALAZAČE SA NJIHOVIM IZUMIMA'";
        try {
            Assert.assertEquals(actual, pairsModel.getHeadline());
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void setColumn1() {
        String column1 = "'1:2 I 7, 2:3 I 8, 3:9 I 6, 4:2 I 4, 5:4 I 7, 6:2 I 11, 7:15 I 6, 8:18 I 4'";
        try {
            Assert.assertNotEquals(column1, pairsModel.getColumn1());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        pairsModel.setHeadline("'1:2 I 7, 2:3 I 8, 3:9 I 6, 4:2 I 4, 5:4 I 7, 6:2 I 11, 7:15 I 6, 8:18 I 4'");
        String actual = "";
        try {
            Assert.assertNotEquals(actual, pairsModel.getColumn1());
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void setColumn2() {
        String column2 = "'2:24, 5:28, 8:36, 3:18, 6:22, 1:14, 4:4, 7:30'";
        try {
            Assert.assertNotEquals(column2, pairsModel.getColumn2());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        pairsModel.setColumn2("'2:24, 5:28, 8:36, 3:18, 6:22, 1:14, 4:4, 7:30'");
        String actual = "'2:24, 5:28, 8:36, 3:18, 6:22, 1:14, 4:4, 7:30'";
        try {
            Assert.assertEquals(actual, pairsModel.getColumn2());
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void testToString() {
        String expected = "";
       Assertions.assertNotEquals(expected, pairsModel.toString());
    }

    @Test
    public void contructorTest() {
        Long id = 1L;
        pairsModel.setId(1L);
        String headLine = "'SPOJITE PRONALAZAČE SA NJIHOVIM IZUMIMA'";
        String column1 = "'1:INDUKCIONA MAŠINA, 2:PENICILIN, 3:ELEKTRONSKA POŠTA, 4: NUKLEARNI REAKTOR, 5:GROMOBRAN, 6:ŽIROSKOP, 7:TRAKTOR, 8:RADIO TALASI'";
        String column2 = "'4:ENRIKO FERMI, 6:LEON FUKO, 8:HAJNRIH HERC, 1:NIKOLA TESLA, 3:REJ TOMPLINSON, 7: BENDŽAMIN HOLT, 2: ALEKSANDAR FLEMING, 5:BENDŽAMIN FRENKLIN'";



        pairsModel = new PairsModel(1L,
                "'SPOJITE PRONALAZAČE SA NJIHOVIM IZUMIMA'",
                "'1:INDUKCIONA MAŠINA, 2:PENICILIN, 3:ELEKTRONSKA POŠTA, 4: NUKLEARNI REAKTOR, 5:GROMOBRAN, 6:ŽIROSKOP, 7:TRAKTOR, 8:RADIO TALASI'",
                "'4:ENRIKO FERMI, 6:LEON FUKO, 8:HAJNRIH HERC, 1:NIKOLA TESLA, 3:REJ TOMPLINSON, 7: BENDŽAMIN HOLT, 2: ALEKSANDAR FLEMING, 5:BENDŽAMIN FRENKLIN'");
        Assert.assertEquals(id, pairsModel.getId());
        Assert.assertEquals(headLine, pairsModel.getHeadline());
        Assert.assertEquals(column1, pairsModel.getColumn1());
        Assert.assertEquals(column2, pairsModel.getColumn2());

        Assert.assertNotNull(pairsModel);
    }
}