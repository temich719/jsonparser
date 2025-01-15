package com.example.jsonparser.controller;

import com.example.jsonparser.exception.CustomParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Controller
public class ExceptionController {

    private static final String ERROR = "error";

    @ExceptionHandler(value = CustomParseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String errorParse() {
        return ERROR;
    }

}
