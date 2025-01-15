package com.example.jsonparser.exception;

import lombok.Getter;

@Getter
public class CustomParseException extends Exception {
    private final String message;

    public CustomParseException(final String message) {
        this.message = message;
    }

}
