package com.comtrade.controller.skockocontroller;

import com.comtrade.model.skockomodel.SkockoGame;
import com.comtrade.model.skockomodel.SkockoResponse;
import com.comtrade.model.skockomodel.SkockoResponseWithPositions;
import com.comtrade.model.skockomodel.SkockoSubmit;
import com.comtrade.repository.skockorepository.SkockoGameRepository;
import com.comtrade.service.skockoservice.SkockoGameServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@WithMockUser(username = "user1", roles = "ADMIN")
class SkockoGameControllerTest {

    @InjectMocks
    SkockoGameController skockoGameController;

    @Mock
    SkockoGameServiceImpl skockoGameService;
    MockMvc mockMvc;
    @Mock
    SkockoGameRepository skockoGameRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(skockoGameController).build();
    }

    @Test
    void getNewGame() throws Exception {
        SkockoGame newGame = new SkockoGame();
        newGame.setId(1L);
        newGame.setCombination(Arrays.asList(1, 2, 3, 4));
        when(skockoGameController.getNewGame(any())).thenReturn(newGame);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/skocko/play").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(" {\"id\":1,\"combination\":[1,2,3,4],\"active\":true}"))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    void submitCombination() throws Exception {
        SkockoSubmit skockoSubmit = new SkockoSubmit(1L,Arrays.asList(0, 2, 3, 4), 2 );
        ObjectMapper mapper=new ObjectMapper();
        String jsonString=mapper.writeValueAsString(skockoSubmit);

        SkockoResponse skockoResponse=new SkockoResponseWithPositions(3,0);
        ResponseEntity<SkockoResponse> response= ResponseEntity.ok(skockoResponse);
        when(skockoGameController.submitCombination(any(), any())).thenReturn(response);
        this.mockMvc
                .perform(post("/skocko/submit")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.goodPositions").exists())
                .andReturn();
    }

    @Test
    void getCombination() throws Exception {
        SkockoGame newGame = new SkockoGame();
        newGame.setId(1L);
        newGame.setCombination(Arrays.asList(1, 2, 3, 4));
        Optional<SkockoGame> newGame2 = Optional.of(newGame);
        when(skockoGameRepository.findById(anyLong())).thenReturn(newGame2);
        ResponseEntity<List<Integer>> response=ResponseEntity.ok(Arrays.asList(1, 2, 3, 4));
        when(skockoGameController.getCombination(any())).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/skocko/getCombination")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect((content().json("[1,2,3,4]",false)))
                .andExpect(status().isOk());
    }
}