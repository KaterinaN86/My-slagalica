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
public class WordModelTest {

    WordModel wordModel;

    private final ErrorCollector ec = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        wordModel = new WordModel();
    }

    @Test
    public void getId() {
        Long id = 1L;
        wordModel.setId(1L);
        Assertions.assertEquals(id, wordModel.getId());
    }

    @Test
    public void getColumna() {
        String columnA = "'SAT,ĆELIJA,KINA,ŽIVI,ZID'";
        wordModel.setColumna("'SAT,ĆELIJA,KINA,ŽIVI,ZID'");
        Assert.assertEquals(columnA, wordModel.getColumna());

    }

    @Test
    public void getColumnb() {
        String columnB = "'KADA,OGLEDALO,TURSKA,TUŠ,KUPATILO'";
        wordModel.setColumnb("'KADA,OGLEDALO,TURSKA,TUŠ,KUPATILO'");
        Assert.assertEquals(columnB, wordModel.getColumnb());
    }

    @Test
    public void getColumnc() {
        String columnC = "'PEDALA,ZAUSTAVLJANJE,RUĆNA,ULJE,KOČNICA'";
        wordModel.setColumnc("'PEDALA,ZAUSTAVLJANJE,RUĆNA,ULJE,KOČNICA'");
        Assert.assertEquals(columnC, wordModel.getColumnc());
    }

    @Test
    public void getColumnd() {
        String columnD = "'TRUDNICA,GRČ,VARENJE,KISELINA,STOMAK'";
        wordModel.setColumnd("'TRUDNICA,GRČ,VARENJE,KISELINA,STOMAK'");
        Assert.assertEquals(columnD, wordModel.getColumnd());
    }

    @Test
    public void getFinalWord() {
        String finalWord = "'PLOČICE'";
        wordModel.setFinalWord("'PLOČICE'");
        Assert.assertEquals(finalWord, wordModel.getFinalWord());
    }

    @Test
    public void setId() {
        Long id = 1L;
        try {
            Assert.assertNotEquals(id, wordModel.getId());
        } catch (Exception e) {
            ec.addError(e);
        }

        wordModel.setId(1L);
        Long actual = 1L;
        id = wordModel.getId();
        try {
            Assert.assertEquals(actual, id);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setColumna() {
        String columnA = "'SAT,ĆELIJA,KINA,ŽIVI,ZID'";
        try {
            Assert.assertNotEquals(columnA, wordModel.getColumna());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        wordModel.setColumna("'SAT,ĆELIJA,KINA,ŽIVI,ZID'");
        String actual = "'SAT,ĆELIJA,KINA,ŽIVI,ZID'";
        columnA = wordModel.getColumna();
        try {
            Assert.assertEquals(actual, columnA);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void setColumnb() {
        String columnB = "'KADA,OGLEDALO,TURSKA,TUŠ,KUPATILO'";
        try {
            Assert.assertNotEquals(columnB, wordModel.getColumnb());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        wordModel.setColumnb("'KADA,OGLEDALO,TURSKA,TUŠ,KUPATILO'");
        String actual = "'KADA,OGLEDALO,TURSKA,TUŠ,KUPATILO'";
        columnB = wordModel.getColumnb();
        try {
            Assert.assertEquals(actual, columnB);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void setColumnc() {
        String columnC = "'PEDALA,ZAUSTAVLJANJE,RUĆNA,ULJE,KOČNICA'";
        try {
            Assert.assertNotEquals(columnC, wordModel.getColumnc());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        wordModel.setColumnc("'PEDALA,ZAUSTAVLJANJE,RUĆNA,ULJE,KOČNICA'");
        String actual = "'PEDALA,ZAUSTAVLJANJE,RUĆNA,ULJE,KOČNICA'";
        columnC = wordModel.getColumnc();
        try {
            Assert.assertEquals(actual, columnC);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void setColumnd() {
        String columnD = "'TRUDNICA,GRČ,VARENJE,KISELINA,STOMAK'";
        try {
            Assert.assertNotEquals(columnD, wordModel.getColumnd());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        wordModel.setColumnd("'TRUDNICA,GRČ,VARENJE,KISELINA,STOMAK'");
        String actual = "'TRUDNICA,GRČ,VARENJE,KISELINA,STOMAK'";
        columnD = wordModel.getColumnd();
        try {
            Assert.assertEquals(actual, columnD);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void setFinalWord() {
        String finalWord = "'PLOČICE'";
        try {
            Assert.assertNotEquals(finalWord, wordModel.getFinalWord());
        } catch (NullPointerException npe) {
            ec.addError(npe);
        }
        wordModel.setFinalWord("'PLOČICE'");
        String actual = "'PLOČICE'";
        finalWord = wordModel.getFinalWord();
        try {
            Assert.assertEquals(actual, finalWord);
        } catch (NoSuchElementException nse) {
            ec.addError(nse);
        }
    }

    @Test
    public void contructorTest() {
        String columnA = "'SAT,ĆELIJA,KINA,ŽIVI,ZID'";
        String columnB = "'KADA,OGLEDALO,TURSKA,TUŠ,KUPATILO'";
        String columnC = "'PEDALA,ZAUSTAVLJANJE,RUĆNA,ULJE,KOČNICA'";
        String columnD = "'TRUDNICA,GRČ,VARENJE,KISELINA,STOMAK'";
        String finalWord = "'PLOČICE'";


        wordModel = new WordModel(1L,
                "'SAT,ĆELIJA,KINA,ŽIVI,ZID'",
                "'KADA,OGLEDALO,TURSKA,TUŠ,KUPATILO'",
                "'PEDALA,ZAUSTAVLJANJE,RUĆNA,ULJE,KOČNICA'",
                "'TRUDNICA,GRČ,VARENJE,KISELINA,STOMAK'",
                "'PLOČICE'");
        Assert.assertEquals(columnA, wordModel.getColumna());
        Assert.assertEquals(columnB, wordModel.getColumnb());
        Assert.assertEquals(columnC, wordModel.getColumnc());
        Assert.assertEquals(columnD, wordModel.getColumnd());
        Assert.assertEquals(finalWord, wordModel.getFinalWord());
    }
}