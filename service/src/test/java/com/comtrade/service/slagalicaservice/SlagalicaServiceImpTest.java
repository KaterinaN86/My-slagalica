package com.comtrade.service.slagalicaservice;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
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
        when(slagalicaRepository.findById(1L).get().getComputerLongestWord().length()).thenReturn(anyInt());
        slagalicaUserWordSubmit = SlagalicaUserWordSubmit.builder()
                                                         .gameId(GAME_ID)
                                                         .userWord(userWord)
                                                         .lettersForFindingTheWord(slagalica.getLettersForFindingTheWord())
                                                         .build();
        assertEquals(userWord.length()*2, slagalicaService.userWordProcessing(slagalicaUserWordSubmit));
        Assertions.assertNotNull(slagalicaService.userWordProcessing(slagalicaUserWordSubmit));
    }
}