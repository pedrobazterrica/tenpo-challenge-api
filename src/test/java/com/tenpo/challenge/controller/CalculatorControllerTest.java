package com.tenpo.challenge.controller;

import com.tenpo.challenge.config.RateLimiter;
import com.tenpo.challenge.config.RateLimiterInterceptor;
import com.tenpo.challenge.config.RequestFilter;
import com.tenpo.challenge.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CalculatorController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {RequestFilter.class}))
public class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RateLimiter rateLimiter;
    @MockBean
    private CalculatorService calculatorService;

    @BeforeEach
    void setup(){
        when(rateLimiter.tryAccess(anyString())).thenReturn(true);
    }

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        when(calculatorService.calculateValue(any(), any())).thenReturn(11d);
        mockMvc.perform(get("/api/v1/calculate/numbers/5/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("11.0"));
    }

    @Test
    @Disabled
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
