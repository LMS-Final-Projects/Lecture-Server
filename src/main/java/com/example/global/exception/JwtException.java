package com.example.global.exception;

import lombok.Getter;

@Getter
public class JwtException extends RuntimeException{
    public JwtException(String message) {
        super(message);
    }
}
