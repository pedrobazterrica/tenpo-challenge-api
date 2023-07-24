package com.tenpo.challenge.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.HttpServerErrorException;

public interface PercentageClient {
    @Cacheable("percentage")
    Double getPercentage() throws HttpServerErrorException;
}
