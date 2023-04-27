package com.reto.usuario.domain.api;

import com.reto.usuario.domain.dto.AuthCredentials;
import com.reto.usuario.domain.model.UserModel;

public interface IUserServicePort {

    void registerUserWithOwnerRole(UserModel userModel);

    void registerUserWithEmployeeRole(UserModel userModel);

    void registerUserWithCustomerRole(UserModel userModel);

    String signInUseCase(AuthCredentials authCredentials);

    UserModel findUsuarioByEmail(String email);

    UserModel getUserById(long idUser);
}
