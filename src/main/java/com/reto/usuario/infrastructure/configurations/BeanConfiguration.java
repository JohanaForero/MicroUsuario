package com.reto.usuario.infrastructure.configurations;

import com.reto.usuario.domain.serviceproviderinterface.IRolPersistenceDomainPort;
import com.reto.usuario.domain.api.IUserServicePort;
import com.reto.usuario.domain.usecase.UserUseCaseImpl;
import com.reto.usuario.infrastructure.drivenadapter.mapper.IRolEntityMapper;
import com.reto.usuario.infrastructure.drivenadapter.mapper.IUserEntityMapper;
import com.reto.usuario.infrastructure.drivenadapter.persistence.RolPersistenceDomainPortImpl;
import com.reto.usuario.infrastructure.drivenadapter.persistence.UserPersistenceDomainPortImpl;
import com.reto.usuario.infrastructure.drivenadapter.repository.IRolRepositoryMysql;
import com.reto.usuario.infrastructure.drivenadapter.repository.IUserRepositoryMysql;
import com.reto.usuario.domain.serviceproviderinterface.IUserPersistenceDomainPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class BeanConfiguration {

    private final IUserRepositoryMysql userRepositoryMysql;
    private final IUserEntityMapper userEntityMapper;
    private final IRolRepositoryMysql rolRepositoryMysql;
    private final IRolEntityMapper rolEntityMapper;

    @Bean
    public IUserPersistenceDomainPort userPersistencePort() {
        return new UserPersistenceDomainPortImpl(userRepositoryMysql, userEntityMapper);
    }

    @Bean
    public IUserServicePort userRepositoryPortDomain() {
        return new UserUseCaseImpl(userPersistencePort(), rolesPersistencePort());
    }

    @Bean
    public IRolPersistenceDomainPort rolesPersistencePort() {
        return new RolPersistenceDomainPortImpl(rolRepositoryMysql, rolEntityMapper);
    }
}
