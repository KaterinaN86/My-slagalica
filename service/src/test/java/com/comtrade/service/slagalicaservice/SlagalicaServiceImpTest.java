package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.DictionaryWord;
import com.comtrade.model.slagalicamodel.Slagalica;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.repository.slagalicarepository.DictionaryWordRepository;
import com.comtrade.repository.slagalicarepository.SlagalicaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
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
        slagalicaRepository.save(Slagalica.builder()
                .lettersForFindingTheWord("IMAASIRKENKJ")
                .computerLongestWord("MASKE").build());
        dictionaryWordRepository.save(DictionaryWord.builder().wordFromDictionary("MASKIRANJE").build());
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

        String userWord = "MASKIRANJE";
        String lettersForFindingTheWord = "IMAASIRKENKJ";
        List<Slagalica> slagalicaList = new ArrayList<>();
        slagalica = Slagalica.builder()
                             .id(GAME_ID)
                             .lettersForFindingTheWord(lettersForFindingTheWord)
                             .computerLongestWord("MASKE")
                             .build();

        slagalicaList.add(slagalica);
        when(slagalicaRepository.findAll()).thenReturn(slagalicaList);
        slagalicaUserWordSubmit = SlagalicaUserWordSubmit.builder()
                                                         .gameId(GAME_ID)
                                                         .userWord(userWord)
                                                         .lettersForFindingTheWord(slagalica.getLettersForFindingTheWord())
                                                         .build();

        assertNotNull(slagalicaService.userWordProcessing(slagalicaUserWordSubmit));
        //assertEquals(userWord.length()*2, slagalicaService.userWordProcessing(slagalicaUserWordSubmit));
    }

    @Test
    void testComputersLongestWord() {

        String lettersForWord = "ODIŽOSIRAĐAM";
        List<DictionaryWord> dictionaryWordList = Arrays.asList(DictionaryWord.builder().wordFromDictionary("DOS").build());
        when(dictionaryWordRepository.findAll()).thenReturn(dictionaryWordList);
        assertEquals("DOS", slagalicaService.computersLongestWord(lettersForWord));
    }
}