package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.Slagalica;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SlagalicaServiceImpTest {

    @Mock
    SlagalicaRepository slagalicaRepository;

    SlagalicaService slagalicaService;

    Slagalica slagalica;
    SlagalicaUserWordSubmit slagalicaUserWordSubmit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        slagalicaService = new SlagalicaServiceImp(slagalicaRepository);
    }

    @Test
    void testSaveLetterForFindingWords() {

        Slagalica slagalicaGameToSave = Slagalica.builder().id(1L).build();
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
        slagalica = Slagalica.builder().id(1L).lettersForFindingTheWord("IMAASIRKENKJ").build();
        slagalicaUserWordSubmit = SlagalicaUserWordSubmit.builder()
                                                         .gameId(2L)
                                                         .userWord(userWord)
                                                         .lettersForFindingTheWord(slagalica.getLettersForFindingTheWord())
                                                         .build();

        assertEquals(userWord.length()*2, slagalicaService.userWordProcessing(slagalicaUserWordSubmit));
        Assertions.assertNotNull(slagalicaService.userWordProcessing(slagalicaUserWordSubmit));
    }
}