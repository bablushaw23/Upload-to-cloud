package com.example.pdfuploads3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidObjectException extends RuntimeException{
    public InvalidObjectException(String msg){
        super(msg);
    }
}
