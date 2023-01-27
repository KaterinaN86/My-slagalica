package com.comtrade.model.asocijacijamodel;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ErrorCollector;

import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testng.Assert;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AsocijacijaGame.class)
public class AsocijacijaGameTest {

    @Mock
    AsocijacijaGame asocijacijaGame;
    @Mock
    WordModel wordModel;

    private final ErrorCollector ec = new ErrorCollector();

    @BeforeEach
    public void setUp() {
        asocijacijaGame = new AsocijacijaGame();
    }

    @Test
    public void getId() {
        Long id = 1L;
        asocijacijaGame.setId(1L);
        Assert.assertEquals(id, asocijacijaGame.getId());
    }

    @Test
    public void getWordModel() {
        wordModel = new WordModel(1L, "'TRUDNICA,GRČ,VARENJE,KISELINA,STOMAK'","'SAT,ĆELIJA,KINA,ŽIVI,ZID'", "'KADA,OGLEDALO,TURSKA,TUŠ,KUPATILO'", "'PEDALA,ZAUSTAVLJANJE,RUĆNA,ULJE,KOČNICA'", "'PLOČICE'");
        Assert.assertNotNull(wordModel);
    }

    @Test
    public void setId() {
        Long expected = 1L;
        try {
            Assert.assertNotEquals(expected, asocijacijaGame.getId());
        }
        catch (Exception e) {
            ec.addError(e);
        }

        asocijacijaGame.setId(1L);
        Long actual = 1L;
        expected = asocijacijaGame.getId();
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void setWordModel() {
        wordModel = new WordModel(1L, "'TRUDNICA,GRČ,VARENJE,KISELINA,STOMAK'","'SAT,ĆELIJA,KINA,ŽIVI,ZID'", "'KADA,OGLEDALO,TURSKA,TUŠ,KUPATILO'", "'PEDALA,ZAUSTAVLJANJE,RUĆNA,ULJE,KOČNICA'", "'PLOČICE'");
        try {
            Assert.assertNotNull(wordModel);
        }
        catch (Exception e) {
            ec.addError(e);
        }

        try {
            Assert.assertNotNull(wordModel);
        } catch (Exception e) {
            ec.addError(e);
        }
    }

    @Test
    public void constructorTest() {
        wordModel = new WordModel(1L, "'TRUDNICA,GRČ,VARENJE,KISELINA,STOMAK'","'SAT,ĆELIJA,KINA,ŽIVI,ZID'", "'KADA,OGLEDALO,TURSKA,TUŠ,KUPATILO'", "'PEDALA,ZAUSTAVLJANJE,RUĆNA,ULJE,KOČNICA'", "'PLOČICE'");
        Long id = 1L;
        asocijacijaGame = new AsocijacijaGame(id, wordModel);
        Assert.assertEquals(id, wordModel.getId());
        Assert.assertEquals(wordModel, asocijacijaGame.getWordModel());
    }
}