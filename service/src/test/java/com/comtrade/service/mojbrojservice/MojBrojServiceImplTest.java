package com.comtrade.service.mojbrojservice;

import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.repository.mojbrojrepository.MojBrojRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class MojBrojServiceImplTest {

    @Mock
    MojBrojRepository mojBrojRepository;
    MojBrojService mojBrojService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mojBrojService=new MojBrojServiceImpl(mojBrojRepository, gamerepository);
    }

    @Test
    void createNewGame() {
        MojBrojGame mojBrojGame=mojBrojService.createNewGame();
        MojBrojGame mojBrojGame1=mojBrojService.createNewGame();
        MojBrojGame mojBrojGame2=mojBrojService.createNewGame();
        MojBrojGame mojBrojGame3=mojBrojService.createNewGame();
        assertTrue(mojBrojGame.isActive());
        assertTrue(mojBrojGame.getNumbers()!=null);
        assertTrue(mojBrojGame.getSolution().getClass()==String.class);
    }
    @Test
    void createNewGameWithParametars(){
        long id=1l;
        ArrayList<Integer> list=new ArrayList<>();
        String solution="no Solution";
        boolean active=true;
        MojBrojGame game=new MojBrojGame(id,list,active,solution);
        MojBrojGame mojBrojGame=mojBrojService.createNewGame(id,list,active,solution);
        assertEquals(game.getId(),mojBrojGame.getId());
        assertEquals(game.getNumbers(),mojBrojGame.getNumbers());
        assertEquals(game.getSolution(),mojBrojGame.getSolution());
        assertEquals(game.isActive(),mojBrojGame.isActive());
    }


    @Test
    void validateExpression() {
        ArrayList<Integer> list=new ArrayList<>();
        list.add(6);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(15);
        list.add(25);
        MojBrojGame game=new MojBrojGame(1L,list,true,"1+2+3");

        Mockito.when(mojBrojRepository.findById(1l)).thenReturn(Optional.of(game));

        assertTrue(mojBrojService.validateExpression("1+2+3+15",1l));
        assertTrue(mojBrojService.validateExpression("(1-2)+3+15",1l));
        assertTrue(mojBrojService.validateExpression("(1*25+3+15)",1l));
        assertTrue(mojBrojService.validateExpression("1+(2-(3+15))",1l));
        assertFalse(mojBrojService.validateExpression("(1-1)+1",2L));
        assertFalse(mojBrojService.validateExpression("(1-)+1",1L));
        assertFalse(mojBrojService.validateExpression("(1-)+1(",1L));
        assertFalse(mojBrojService.validateExpression("(1-)+1)",1L));
        assertFalse(mojBrojService.validateExpression("1+1+3+15",1l));
    }

    @Test
    void eval() {
        try {
            assertEquals(6,mojBrojService.eval("1+2+3"));
            assertEquals(-117,mojBrojService.eval("(1+7)-5*25"));
            assertEquals(100,mojBrojService.eval("7*100/(4+3)"));
            assertEquals(15,mojBrojService.eval("2*5*25/15-7/8"));
        } catch (ScriptException e) {
            fail();
        }
    }

    @Test
    void userSolutionDiff() {
        ArrayList<Integer> list=new ArrayList<>();
        list.add(17);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(10);
        list.add(25);
        Optional<MojBrojGame> game=Optional.empty();
        MojBrojGame game1=new MojBrojGame(1l,list,true,"1+10+3*2+1");
        MojBrojGame game2=new MojBrojGame(2l,list,false,"3+2+4-(25/5)");
        MojBrojGame game3=new MojBrojGame(3l,list,true,"3+2+4-(25/5))");
        MojBrojGame game4=new MojBrojGame(4l,list,true,"1+10+3*2+1");


        Mockito.when(mojBrojRepository.findById(1l)).thenReturn(Optional.of(game1));
        Mockito.when(mojBrojRepository.findById(2l)).thenReturn(Optional.of(game2));
        Mockito.when(mojBrojRepository.findById(3l)).thenReturn(Optional.of(game3));
        Mockito.when(mojBrojRepository.findById(4l)).thenReturn(Optional.of(game4));
        Mockito.when(mojBrojRepository.findById(5l)).thenReturn(game);
        try {
            assertEquals(14,mojBrojService.userSolutionDiff("1+2",1l));
            assertEquals(4,mojBrojService.userSolutionDiff("1+2+10",4l));
            Exception e=assertThrows(Exception.class,()->{mojBrojService.userSolutionDiff("1+2",2l);});
            Exception e1=assertThrows(Exception.class,()->{mojBrojService.userSolutionDiff("1+2(",3l);});
            Exception e2=assertThrows(Exception.class,()->{mojBrojService.userSolutionDiff("1+2",5l);});
            assertEquals(e.getMessage(),"You can submit only once");
            assertEquals(e1.getMessage(),"Bad expression");
            assertEquals(e2.getMessage(),"Wrong game id");
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    void getSolution() {
        MojBrojGame game=new MojBrojGame(1l,new ArrayList<>(),true,"3+2+4-(2/5)");
        Mockito.when(mojBrojRepository.findById(1l)).thenReturn(Optional.of(game));
        assertEquals("3+2+4-(2/5)",mojBrojService.getSolution(1l));
    }
}