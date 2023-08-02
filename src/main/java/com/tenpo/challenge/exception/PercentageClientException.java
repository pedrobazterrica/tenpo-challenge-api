package com.tenpo.challenge.exception;

public class PercentageClientException extends RuntimeException{
    public PercentageClientException(String message, Exception e){
        super(message, e);
    }
}
