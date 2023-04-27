package com.reto.usuario.infrastructure.entrypoint;

import com.reto.usuario.application.dto.request.UserRequestDto;
import com.reto.usuario.application.dto.response.UserResponseDto;
import com.reto.usuario.application.handler.IUserHandler;
import com.reto.usuario.application.dto.request.AuthCredentialsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/usuario-micro/usuario")
public class UserRestController {

    private final IUserHandler userHandler;

    @PostMapping(value = "/")
    @PreAuthorize(value = "hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> registerUserAsOwner(@RequestBody UserRequestDto usuarioRequestDto) {
        userHandler.registerOwnerUser(usuarioRequestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody AuthCredentialsRequest authCredentialsRequest) {
        return ResponseEntity.ok(userHandler.singIn(authCredentialsRequest));
    }

    @GetMapping(value = "/verifier")
    @PreAuthorize(value = "hasRole('ADMINISTRADOR') or hasRole('EMPLEADO') or hasRole('PROPIETARIO')")
    public ResponseEntity<UserResponseDto> getUserById(@RequestParam(name = "iduser", required = false) Long idUser) {
        if(idUser != null) {
            return new ResponseEntity<>(userHandler.getUserById(idUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
