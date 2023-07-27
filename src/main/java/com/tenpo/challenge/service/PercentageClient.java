package com.tenpo.challenge.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public interface PercentageClient {
    @Cacheable("percentage")
    @Retryable(value = {HttpServerErrorException.class, HttpClientErrorException.class},
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.delay}"))
    Double getPercentage() throws HttpServerErrorException, HttpClientErrorException;
}
