package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.ApiErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorMessage> handleGenericException(Exception exception, WebRequest request) {
        ApiErrorMessage error = buildErrorMessage(exception, request);
        log.error(exception.getMessage(), error);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    private ApiErrorMessage buildErrorMessage(Exception e, WebRequest request){
        ApiErrorMessage error = new ApiErrorMessage();
        error.setDescription(e.getMessage());
        if (request != null) {
            error.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        }
        error.setTimestamp(LocalDateTime.now());
        return error;
    }
}
