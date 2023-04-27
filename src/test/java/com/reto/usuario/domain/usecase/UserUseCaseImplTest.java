package com.reto.usuario.domain.usecase;

import com.reto.usuario.domain.dto.AuthCredentials;
import com.reto.usuario.domain.exception.EmailException;
import com.reto.usuario.domain.exception.EmptyFieldsException;
import com.reto.usuario.domain.exception.InvalidCellPhoneFormatException;
import com.reto.usuario.domain.model.UserModel;
import com.reto.usuario.domain.serviceproviderinterface.IRolPersistenceDomainPort;
import com.reto.usuario.domain.serviceproviderinterface.IUserPersistenceDomainPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserUseCaseImplTest {

    @InjectMocks
    UserUseCaseImpl userUseCase;

    @Mock
    IUserPersistenceDomainPort userPersistenceDomainPort;

    @Mock
    IRolPersistenceDomainPort rolPersistenceDomainPort;


    @Test
    void registerUserWithOwnerRole() {
        when(rolPersistenceDomainPort.findByNombre("PROPIETARIO")).thenReturn(FactoryUseModelTest.rolModel());
        when(userPersistenceDomainPort.saveUser(any())).thenReturn(FactoryUseModelTest.userModelWithoutRole());
        userUseCase.registerUserWithOwnerRole(FactoryUseModelTest.userModel());
        verify(userPersistenceDomainPort).saveUser(any(UserModel.class));
    }

    @Test
    void signInUseCase() {
        AuthCredentials authCredentials = new AuthCredentials();
        authCredentials.setEmail("test@gmail.com");
        authCredentials.setPassword("12345678");
        when(userPersistenceDomainPort.findByEmail(authCredentials.getEmail())).thenReturn(
                Optional.of(FactoryUseModelTest.userModel()));
        String token = userUseCase.signInUseCase(authCredentials);
        assertNotNull(token);
        UsernamePasswordAuthenticationToken userAutheticated = TokenUtils.getAuthentication(token);
        Assertions.assertEquals(FactoryUseModelTest.userModel().getEmail(), userAutheticated.getName());
        verify(userPersistenceDomainPort).findByEmail(authCredentials.getEmail());
    }

    @Test
    void findUsuarioByCorreo() {
        when(userPersistenceDomainPort.findByEmail("test-email@gmail.com")).thenReturn(Optional.of(FactoryUseModelTest.userModel()));
        UserModel userModel = userUseCase.findUsuarioByEmail("test-email@gmail.com");
        Assertions.assertEquals(FactoryUseModelTest.userModel().getEmail(), userModel.getEmail());
        verify(userPersistenceDomainPort).findByEmail(any(String.class));
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> { userUseCase.findUsuarioByEmail("test-email-not-found@gmail.com"); }
        );
    }

    @Test
    void throwEmptyFieldsExceptionWhenRegisterUserWithOwnerRole() {
        UserModel userModelWithFieldsEmpty =
                FactoryUseModelTest.userModelFieldsEmpty();
        Assertions.assertThrows(
                EmptyFieldsException.class,
                () -> { userUseCase.registerUserWithOwnerRole(userModelWithFieldsEmpty); }
        );
    }

    @Test
    void throwEmailStructureInvalidExceptionWhenRegisterUserWithOwnerRole() {
        UserModel userModelEmailStructureInvalid =
                FactoryUseModelTest.userModelEmailStructureInvalid();
        Assertions.assertThrows(
                EmailException.class,
                () -> { userUseCase.registerUserWithOwnerRole(userModelEmailStructureInvalid); }
        );
    }

    @Test
    void throwInvalidCellPhoneFormatExceptionWhenRegisterUserWithOwnerRole() {
        UserModel userModelCellPhoneInvalid =
                FactoryUseModelTest.userModelCellPhoneInvalid();
        Assertions.assertThrows(
                InvalidCellPhoneFormatException.class,
                () -> { userUseCase.registerUserWithOwnerRole(userModelCellPhoneInvalid); }
        );
    }
}