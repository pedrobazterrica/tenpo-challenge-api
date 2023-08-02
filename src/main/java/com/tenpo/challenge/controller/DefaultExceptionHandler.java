package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.ApiErrorMessage;
import com.tenpo.challenge.exception.PercentageClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorMessage> handleGenericException(Exception exception, WebRequest request) {
        ResponseEntity<ApiErrorMessage> error = buildErrorMessage(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
        log.error(exception.getMessage(), error.getBody());
        return error;
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ApiErrorMessage> handleGenericException(HttpStatusCodeException exception, WebRequest request) {
        ResponseEntity<ApiErrorMessage> error = buildErrorMessage(exception, exception.getStatusCode(), request);
        log.error(exception.getMessage(), error.getBody());
        return error;
    }


    @ExceptionHandler(PercentageClientException.class)
    public ResponseEntity<ApiErrorMessage> handlePercentageClientException(PercentageClientException exception, WebRequest request) {
        ResponseEntity<ApiErrorMessage> error = buildErrorMessage(exception, HttpStatus.SERVICE_UNAVAILABLE, request);
        log.error(exception.getMessage(), error.getBody());
        return error;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var apiError = buildErrorMessage(e, status, request).getBody();
        return super.handleExceptionInternal(e, apiError, headers, status, request);
    }

    private ResponseEntity<ApiErrorMessage> buildErrorMessage(Exception e, HttpStatus status, WebRequest request) {
        ApiErrorMessage error = new ApiErrorMessage();
        error.setDescription(status.getReasonPhrase());
        error.setMessage(e.getMessage());
        if (request != null) {
            error.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        }
        error.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(status).body(error);
    }
}
