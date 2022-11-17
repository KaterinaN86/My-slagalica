package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.Slagalica;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.repository.slagalicarepository.SlagalicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SlagalicaServiceImpTest {

    @Mock
    SlagalicaRepository slagalicaRepository;
    @Mock
    ResourceUtils resourceUtils;
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
        when(slagalicaRepository.save(any())).thenReturn(slagalicaGameToSave);
        Slagalica savedSlagalicaGame = slagalicaService.saveLetterForFindingWords();

        assertNotNull(savedSlagalicaGame);
    }

    @Test
    void testLettersForFindingTheWord() {

        String expectedLettersForWord = "IMABSIRKENKJ";

        assertEquals(expectedLettersForWord.length(), slagalicaService.lettersForFindingTheWord().length());
        assertNotNull(slagalicaService.lettersForFindingTheWord());

    }

    @Test
    void testUserWordProcessing() throws IOException {

        slagalica = null;
        String userWord = "MASKIRANJE";
        slagalica = Slagalica.builder().id(1L).lettersForFindingTheWord("IMABSIRKENKJ").build();
        slagalicaUserWordSubmit = SlagalicaUserWordSubmit.builder()
                                                         .gameId(2L)
                                                         .userWord(userWord)
                                                         .lettersForFindingTheWord(slagalica.getLettersForFindingTheWord())
                                                         .build();

        assertEquals(userWord.length()*2, slagalicaService.userWordProcessing(slagalicaUserWordSubmit));
    }
}