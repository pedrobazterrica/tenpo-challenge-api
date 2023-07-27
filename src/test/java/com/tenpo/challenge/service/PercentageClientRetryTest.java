package com.tenpo.challenge.service;

import com.tenpo.challenge.config.RetryConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {RetryConfiguration.class, PercentageClient.class, PercentageClientImpl.class})
@ActiveProfiles("test")
public class PercentageClientRetryTest {
    @Autowired
    PercentageClient percentageClient;
    @MockBean
    RestTemplate restTemplate;

    @Test
    @Disabled
    void testRetry(){
        when(restTemplate.getForObject(any(),any()))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .thenReturn(5d);
        Double result = percentageClient.getPercentage();
        verify(restTemplate.getForObject(any(),any()), times(2));
        assertEquals(5d, result);
    }
}

