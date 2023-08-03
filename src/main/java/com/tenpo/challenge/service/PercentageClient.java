package com.tenpo.challenge.service;

import com.tenpo.challenge.exception.PercentageClientException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface PercentageClient {
    @Cacheable(value = "percentage")
    @Retryable(value = {PercentageClientException.class},
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.delay}"))
    Double getPercentage();

    @Recover
    Double recover(PercentageClientException e, String sql);
}
