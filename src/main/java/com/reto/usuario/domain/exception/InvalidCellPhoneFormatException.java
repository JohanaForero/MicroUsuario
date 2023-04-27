package com.reto.usuario.domain.exception;

public class InvalidCellPhoneFormatException extends RuntimeException {

    public InvalidCellPhoneFormatException(String message) {
        super(message);
    }
}
