package com.comtrade.backend.controllers;

import com.comtrade.backend.services.SlagalicaService;
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

import static org.junit.jupiter.api.Assertions.*;
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
    void slovaZaPretraguReci() throws Exception {

        String rec = "SADOMIRKIBEO";

        when(slagalicaService.slovaZaPronalazakReci()).thenReturn(rec);

        mockMvc.perform(MockMvcRequestBuilders.get("/slovaZaPretragu"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("slagalica"));

        verify(slagalicaService, times(1)).slovaZaPronalazakReci();

    }
}