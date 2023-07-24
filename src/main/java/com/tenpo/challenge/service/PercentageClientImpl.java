package com.tenpo.challenge.service;

import org.springframework.stereotype.Service;

@Service
public class PercentageClientImpl implements PercentageClient{

    @Override
    public Double getPercentage() {
        return 10d;
    }
}
