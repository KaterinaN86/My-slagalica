package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.DictionaryWord;
import com.comtrade.model.slagalicamodel.Slagalica;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.repository.slagalicarepository.DictionaryWordRepository;
import com.comtrade.repository.slagalicarepository.SlagalicaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SlagalicaServiceImpTest {

    public static final long GAME_ID = 1L;
    @Mock
    SlagalicaRepository slagalicaRepository;
    @Mock
    DictionaryWordRepository dictionaryWordRepository;

    SlagalicaService slagalicaService;

    Slagalica slagalica;
    SlagalicaUserWordSubmit slagalicaUserWordSubmit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        slagalicaService = new SlagalicaServiceImp(slagalicaRepository, dictionaryWordRepository);
    }

    @Test
    void testSaveLetterForFindingWords() {

        Slagalica slagalicaGameToSave = Slagalica.builder().id(GAME_ID).build();
        Mockito.when(slagalicaRepository.save(ArgumentMatchers.any())).thenReturn(slagalicaGameToSave);
        Slagalica savedSlagalicaGame = slagalicaService.saveLetterForFindingWords();

        Assertions.assertNotNull(savedSlagalicaGame);
    }



    @Test
    void testLettersForFindingTheWord() {

        String expectedLettersForWord = "IMABSIRKENKJ";

        assertEquals(expectedLettersForWord.length(), slagalicaService.lettersForFindingTheWord().length());
        Assertions.assertNotNull(slagalicaService.lettersForFindingTheWord());

    }

    @Test
    void testUserWordProcessing() throws IOException {

        slagalica = null;
        String userWord = "MASKIRANJE";
        String lettersForFindingTheWord = "IMAASIRKENKJ";
        slagalica = Slagalica.builder()
                             .id(GAME_ID)
                             .lettersForFindingTheWord(lettersForFindingTheWord)
                             .computerLongestWord("MASKE")
                             .build();
        when(slagalicaRepository.findAll().get(0).getComputerLongestWord().length()).thenReturn(anyInt());
        slagalicaUserWordSubmit = SlagalicaUserWordSubmit.builder()
                                                         .gameId(GAME_ID)
                                                         .userWord(userWord)
                                                         .lettersForFindingTheWord(slagalica.getLettersForFindingTheWord())
                                                         .build();
        assertEquals(userWord.length()*2, slagalicaService.userWordProcessing(slagalicaUserWordSubmit));
        Assertions.assertNotNull(slagalicaService.userWordProcessing(slagalicaUserWordSubmit));
    }

    @Test
    void testComputersLongestWord() {

        String lettersForWord = "UVIBUKUŽAHIC";
        String expectedComputerWord = "UK";
        DictionaryWord dictionaryWordd = DictionaryWord.builder().id(1L).wordFromDictionary("BUKA").build();
        //List<DictionaryWord> dictionaryWordList = new ArrayList<>();
        //when(slagalicaService.computersLongestWord(lettersForWord)).thenReturn(null);
        assertEquals(expectedComputerWord, slagalicaService.computersLongestWord(lettersForWord));
    }
}