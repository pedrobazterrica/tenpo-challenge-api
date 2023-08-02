package com.tenpo.challenge.service;

import com.tenpo.challenge.config.RestTemplateConfig;
import com.tenpo.challenge.config.RetryConfiguration;
import com.tenpo.challenge.dto.PercentageDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

@SpringBootTest(classes = {RetryConfiguration.class, PercentageClient.class, PercentageClientImpl.class, RestTemplateConfig.class})
@ActiveProfiles("test")
public class PercentageClientRetryTest {
    @Autowired
    PercentageClient percentageClient;
    @MockBean
    RestTemplate restTemplate;

    @Test
    void testRetry_shouldBeCalledTwice(){
        PercentageDto percentageDto = new PercentageDto();
        percentageDto.setPercentage(5d);
        when(restTemplate.getForObject(anyString(),eq(PercentageDto.class)))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .thenReturn(percentageDto);
        Double result = percentageClient.getPercentage();
        verify(restTemplate, times(2)).getForObject(anyString(),eq(PercentageDto.class));
        assertEquals(5d, result);
    }
}

