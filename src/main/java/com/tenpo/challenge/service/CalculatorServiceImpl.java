package com.tenpo.challenge.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorServiceImpl implements CalculatorService{
    private final PercentageClient percentageClient;

    public CalculatorServiceImpl(PercentageClient percentageClient) {
        this.percentageClient = percentageClient;
    }

    @Override
    public Double calculateValue(Double num1, Double num2) {
        Double percentage = percentageClient.getPercentage();
        return (num1+num2) + ((num1+num2)*percentage/100);
    }
}
