package com.comtrade.controller.slagalicacontroller;

import com.comtrade.service.slagalicaservice.SlagalicaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SlagalicaControllerTest {

    @Mock
    SlagalicaService slagalicaService;

    @InjectMocks
    SlagalicaController slagalicaController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(slagalicaController).build();
    }

    @Test
    void getLettersForFindingTheWord() throws Exception {

        String word = "SADOMIRKIBEO";

        when(slagalicaService.lettersForFindingTheWord()).thenReturn(word);

        mockMvc.perform(MockMvcRequestBuilders.get("/play"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("slagalica.html"));

        verify(slagalicaService, times(1)).lettersForFindingTheWord();

    }
}