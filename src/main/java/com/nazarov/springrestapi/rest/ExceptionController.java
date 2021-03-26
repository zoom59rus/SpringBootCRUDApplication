package com.nazarov.springrestapi.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Error.class)
    public ResponseEntity exception(Error er){
        String msg = "Какая то ошибка из RestControllerAdvice" + er.getMessage();
        return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
    }
}
