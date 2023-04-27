package com.reto.usuario.infrastructure.drivenadapter.persistence;

import com.reto.usuario.domain.model.UserModel;
import com.reto.usuario.infrastructure.drivenadapter.repository.IUserRepositoryMysql;
import com.reto.usuario.domain.serviceproviderinterface.IUserPersistenceDomainPort;
import com.reto.usuario.infrastructure.drivenadapter.mapper.IUserEntityMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserPersistenceDomainPortImpl implements IUserPersistenceDomainPort {

    private final IUserRepositoryMysql userRepositoryMysql;
    private final IUserEntityMapper userEntityMapper;

    public UserPersistenceDomainPortImpl(IUserRepositoryMysql userRepositoryMysql, IUserEntityMapper userEntityMapper) {
        this.userRepositoryMysql = userRepositoryMysql;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public UserModel saveUser(UserModel userModel) {
        return userEntityMapper.toUserModel(
                userRepositoryMysql.save(userEntityMapper.toUserEntity(userModel)));
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return Optional.of(userEntityMapper.toUserModel(
                userRepositoryMysql.findByEmail(email).orElseThrow(() ->
                        new UsernameNotFoundException("The email " + email + " exists already"))));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepositoryMysql.existsByEmail(email);
    }

    @Override
    public UserModel findById(long idUser) {
        return userEntityMapper.toUserModel(
                userRepositoryMysql.findById(idUser).orElse(null));
    }
}
