package com.reto.usuario.domain.serviceproviderinterface;

import com.reto.usuario.domain.model.UserModel;

import java.util.Optional;

public interface IUserPersistenceDomainPort {

    UserModel saveUser(UserModel usuarioDomain);

    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);

    UserModel findById(long idUser);
}
