package com.tenpo.challenge.controller;

import com.tenpo.challenge.service.CalculatorService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/calculate/numbers/{number1}/{number2}")
    public Double getValue(@PathVariable("number1") @Validated Double number1, @PathVariable("number2") @Validated Double number2){
        return calculatorService.calculateValue(number1,number2);
    }
}
