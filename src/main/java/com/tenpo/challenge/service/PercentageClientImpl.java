package com.tenpo.challenge.service;

import com.tenpo.challenge.dto.PercentageDto;
import com.tenpo.challenge.exception.PercentageClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class PercentageClientImpl implements PercentageClient {

    private final RestTemplate restTemplate;
    private final String url;

    private Double recoverValue;

    public PercentageClientImpl(RestTemplate restTemplate, @Value("${percentage.client.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.recoverValue = null;
    }

    @Override
    public Double getPercentage() {
        try {
            log.info("Trying to get percentage from client: {}", url);
            Double response = restTemplate.getForObject(url, PercentageDto.class).getPercentage();
            this.recoverValue = response;
            return response;
        } catch (Exception e) {
            log.error("There was an error trying to get percentage from {}", url, e.getMessage());
            throw new PercentageClientException("Percentage client is unavailable at the moment, please try again later.", e);
        }
    }

    @Override
    public Double recover(PercentageClientException e, String sql) {
        log.info("Percentage client was not reachable, recovering last fetched value.");
        return Optional.ofNullable(recoverValue).orElseThrow(() -> e);
    }
}
