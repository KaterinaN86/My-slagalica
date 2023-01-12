package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.DictionaryWord;
import com.comtrade.model.slagalicamodel.SlagalicaGame;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.repository.gamerepository.OnePlayerGameRepository;
import com.comtrade.repository.slagalicarepository.DictionaryWordRepository;
import com.comtrade.repository.slagalicarepository.SlagalicaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class SlagalicaOnePlayerGameServiceImpTest {

    public static final long GAME_ID = 1L;
    @Mock
    SlagalicaRepository slagalicaRepository;
    @Mock
    DictionaryWordRepository dictionaryWordRepository;

    @Mock
    OnePlayerGameRepository onePlayerGameRepository;
    SlagalicaService slagalicaService;

    SlagalicaGame slagalicaGame;
    SlagalicaUserWordSubmit slagalicaUserWordSubmit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        slagalicaService = new SlagalicaServiceImp(onePlayerGameRepository, twoPlayerGameRepository, slagalicaRepository, dictionaryWordRepository);
        slagalicaRepository.save(SlagalicaGame.builder()
                .lettersForFindingTheWord("IMAASIRKENKJ")
                .computerLongestWord("MASKE").build());
        dictionaryWordRepository.save(DictionaryWord.builder().wordFromDictionary("MASKIRANJE").build());
    }

    /*@Test
    void testSaveLetterForFindingWords() {

        SlagalicaGame slagalicaGameGameToSave = SlagalicaGame.builder().id(GAME_ID).build();
        Mockito.when(slagalicaRepository.save(ArgumentMatchers.any())).thenReturn(slagalicaGameGameToSave);
        SlagalicaGame savedSlagalicaGameGame = slagalicaService.saveLetterForFindingWords(any());

        Assertions.assertNotNull(savedSlagalicaGameGame);
    }*/



    @Test
    void testLettersForFindingTheWord() {

        String expectedLettersForWord = "IMABSIRKENKJ";

        assertEquals(expectedLettersForWord.length(), slagalicaService.lettersForFindingTheWord().length());
        Assertions.assertNotNull(slagalicaService.lettersForFindingTheWord());

    }

    @Test
    void testUserWordProcessing() throws Exception {

        String userWord = "MASKIRANJE";
        String lettersForFindingTheWord = "IMAASIRKENKJ";
        List<SlagalicaGame> slagalicaGameList = new ArrayList<>();
        slagalicaGame = SlagalicaGame.builder()
                             .id(GAME_ID)
                             .lettersForFindingTheWord(lettersForFindingTheWord)
                             .computerLongestWord("MASKE")
                             .build();

        slagalicaGameList.add(slagalicaGame);
        when(slagalicaRepository.findAll()).thenReturn(slagalicaGameList);
        slagalicaUserWordSubmit = SlagalicaUserWordSubmit.builder()
                                                         .gameId(GAME_ID)
                                                         .userWord(userWord)
                                                         .lettersForFindingTheWord(slagalicaGame.getLettersForFindingTheWord())
                                                         .build();

        assertNotNull(slagalicaService.userWordProcessing(slagalicaUserWordSubmit,any()));
        //assertEquals(userWord.length()*2, slagalicaService.userWordProcessing(slagalicaUserWordSubmit));
    }

    @Test
    void testComputersLongestWord() {

        String lettersForWord = "ODIŽOSIRAĐAM";
        List<DictionaryWord> dictionaryWordList = Collections.singletonList(DictionaryWord.builder().wordFromDictionary("DOS").build());
        when(dictionaryWordRepository.findAll()).thenReturn(dictionaryWordList);
        assertEquals("DOS", slagalicaService.computersLongestWord(lettersForWord));
    }
}