package com.reto.usuario.application.handler;

import com.reto.usuario.application.dto.request.AuthCredentialsRequest;
import com.reto.usuario.application.dto.request.UserRequestDto;
import com.reto.usuario.application.dto.response.UserResponseDto;

public interface IUserHandler {

    public void registerOwnerUser(UserRequestDto userRequestDto);

    public String singIn(AuthCredentialsRequest authCredentialsRequest);

    public UserResponseDto getUserById(long idUser);
}
