package com.tenpo.challenge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PercentageClientImpl implements PercentageClient {

    private final RestTemplate restTemplate;
    private final String url;

    public PercentageClientImpl(RestTemplate restTemplate, @Value("${percentage.client.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    public Double getPercentage() throws HttpServerErrorException, HttpClientErrorException {
        return 10d;
        //return restTemplate.getForObject(url, Double.class);
    }
}
