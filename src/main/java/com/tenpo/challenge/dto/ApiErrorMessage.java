package com.tenpo.challenge.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiErrorMessage {
    private LocalDateTime timestamp;
    private String path;
    private String description;
    private String message;
}
