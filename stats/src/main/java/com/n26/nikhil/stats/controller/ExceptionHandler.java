package com.n26.nikhil.stats.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.n26.nikhil.stats.exception.UnparsableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UnparsableException.class)
    public ResponseEntity<?> handleUnprocessableContent(UnparsableException exp){
        return  ResponseEntity.status(exp.getStatus().value()).body(null);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<?> handleJsonMappingException(JsonMappingException exp){
        return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    }
}
