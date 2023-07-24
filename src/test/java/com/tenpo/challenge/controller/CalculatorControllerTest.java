package com.tenpo.challenge.controller;

import com.tenpo.challenge.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CalculatorController.class)
public class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CalculatorService calculatorService;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        when(calculatorService.calculateValue(any(),any())).thenReturn(11d);
        mockMvc.perform(get("/api/v1/calculate/numbers/5/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("11.0"));
    }

    @Test
    void whenInvalidInput_shouldReturn400BadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/calculate/numbers/5/hola"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidInput_shouldReturn405InvalidMethod() throws Exception {
        mockMvc.perform(post("/api/v1/calculate/numbers/5/5"))
                .andExpect(status().is4xxClientError());
    }
}
