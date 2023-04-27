package com.reto.usuario.infrastructure.exceptionhandler;

import com.reto.usuario.domain.exception.RolNotFoundException;

public enum ExceptionResponse {

    EMAIL_EXCEPTION("The email already exists or incorrect structure"),
    EMPTY_FIELDS_EXCEPTION("Fields cannot be empty"),
    USERNAME_NOT_FOUND_EXCEPTION("Bad credentials"),
    USER_NOT_FOUND_EXCEPTION("User Not Found"),
    INVALID_CELL_PHONE_FORMAT_EXCEPTION("The cell phone format is wrong"),
    ROL_NOT_FOUND_EXCEPTION("Rol Not Found or invalid role");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
