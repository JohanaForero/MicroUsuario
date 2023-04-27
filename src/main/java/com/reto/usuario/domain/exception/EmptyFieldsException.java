package com.reto.usuario.domain.exception;

public class EmptyFieldsException extends RuntimeException {
    
    public EmptyFieldsException(String message) {
        super(message);
    }
}
