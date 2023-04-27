package com.reto.usuario.domain.exception;

public class RolNotFoundException extends RuntimeException {

    public RolNotFoundException(String message) {
        super(message);
    }
}
