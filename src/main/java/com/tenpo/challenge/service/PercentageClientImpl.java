package com.tenpo.challenge.service;

import com.tenpo.challenge.dto.PercentageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
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
        log.info("Trying to get percentage from client: {}", url);
        return restTemplate.getForObject(url, PercentageDto.class).getPercentage();
    }
}
