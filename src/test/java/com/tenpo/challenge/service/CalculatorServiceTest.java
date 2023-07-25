package com.tenpo.challenge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpServerErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {

    CalculatorService calculatorService;
    @Mock
    PercentageClient percentageClient;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorServiceImpl(percentageClient);
    }

    @Test
    void whenCalculateValue_with5plus5_shouldReturn11() {
        when(percentageClient.getPercentage()).thenReturn(10d);
        Double response = calculatorService.calculateValue(5d, 5d);
        assertEquals(11, response);
    }

    @Test
    void whenCalculateValue_andGetPercentageThrowsException_thenThrowException() {
        when(percentageClient.getPercentage()).thenThrow(HttpServerErrorException.InternalServerError.class);
        assertThrows(HttpServerErrorException.InternalServerError.class, () -> calculatorService.calculateValue(5d, 5d));
    }
}
