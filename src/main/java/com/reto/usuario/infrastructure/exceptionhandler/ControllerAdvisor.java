package com.reto.usuario.infrastructure.exceptionhandler;

import com.reto.usuario.domain.exception.EmailException;
import com.reto.usuario.domain.exception.EmptyFieldsException;
import com.reto.usuario.domain.exception.InvalidCellPhoneFormatException;
import com.reto.usuario.domain.exception.RolNotFoundException;
import com.reto.usuario.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<Map<String, String>> handleCorreoException(
            EmailException ignoredCorreoException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMAIL_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(EmptyFieldsException.class)
    public ResponseEntity<Map<String, String>> handleDomainNullException(
            EmptyFieldsException ignoredDomainNullException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EMPTY_FIELDS_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(
            UsernameNotFoundException ignoredUsernameNotFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USERNAME_NOT_FOUND_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException ignoredUserNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USER_NOT_FOUND_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(InvalidCellPhoneFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCellPhoneFormatException(
            InvalidCellPhoneFormatException ignoredInvalidCellPhoneFormatException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_CELL_PHONE_FORMAT_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(RolNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRolNotFoundException(
            RolNotFoundException ignoredRolNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ROL_NOT_FOUND_EXCEPTION.getMessage()));
    }
}
