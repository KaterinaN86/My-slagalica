package com.comtrade.backend.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MojBrojServiceTest {

    private static MojBrojService mbs;
    private static MojBrojService mbs1;
    @BeforeAll
    public static void createClass(){
        mbs=new MojBrojService();
        LinkedList<Integer> ll=new LinkedList<>();
        ll.add(1);
        ll.add(3);
        ll.add(7);
        ll.add(9);
        ll.add(15);
        ll.add(50);
        mbs1=new MojBrojService(121,ll);
    }
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getNums() {
        LinkedList<Integer> ell=new LinkedList<>();
        ell.add(1);
        ell.add(3);
        ell.add(7);
        ell.add(9);
        ell.add(15);
        ell.add(50);
        LinkedList<Integer> all=mbs1.getNums();
        assertEquals(ell,all);
    }

    @Test
    void getTarget() {
        assertEquals(121,mbs1.getTarget());
    }

    @Test
    void testToString() {
        assertEquals("mojBrojService{" +
                "target=" + mbs.getTarget() +
                ", nums=" + mbs.getNums() +
                '}',mbs.toString());
    }

    @Test
    void validateBracket() {
        assertEquals(true,mbs.validateBracket("(1+2)+4*4-1"));
        assertEquals(false,mbs.validateBracket("(1*5/4)-2)+(2+2)"));
        assertEquals(false,mbs.validateBracket("(1*5/4)-2)+(2+2)"));
        assertEquals(false,mbs.validateBracket("(((1*5/4)-2)+(2+2)"));
    }

    @Test
    void eval() {
        assertEquals(String.valueOf(2),mbs.eval("((1+3)-3+7/7*1)"));
        assertEquals(String.valueOf(3),mbs.eval("3"));
    }

    @Test
    void userSolutionDif() {
        assertEquals(Math.abs(mbs.getTarget()-18),mbs.userSolutionDif("((1+2)+4*4-1)"));
        assertEquals(Math.abs(mbs1.getTarget()-20),mbs1.userSolutionDif("((1+2)+4*4+1)"));
    }
}