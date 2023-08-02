package com.tenpo.challenge.service;

import com.tenpo.challenge.exception.PercentageClientException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public interface PercentageClient {
    @Cacheable(value = "percentage")
    @Retryable(value = {PercentageClientException.class},
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.delay}"))
    Double getPercentage() throws HttpServerErrorException, HttpClientErrorException;
}
