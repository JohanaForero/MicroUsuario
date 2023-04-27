package com.reto.usuario.application.handler;

import com.reto.usuario.application.dto.request.AuthCredentialsRequest;
import com.reto.usuario.application.dto.request.UserRequestDto;
import com.reto.usuario.application.dto.request.UserRequestOwnerDto;
import com.reto.usuario.application.dto.response.UserResponseDto;
import com.reto.usuario.domain.model.UserModel;

public interface IUserHandler {

    public void registerUserWithOwnerRole(UserRequestDto userRequestDto);

    public void registerUserWithEmployeeRole(UserRequestOwnerDto userRequestOwnerDto);

    public String singIn(AuthCredentialsRequest authCredentialsRequest);

    public UserResponseDto getUserById(long idUser);
}
