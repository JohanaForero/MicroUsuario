package com.reto.usuario.domain.usecase;

import com.reto.usuario.UsuarioMicroApplication;
import com.reto.usuario.domain.dto.AuthCredentials;
import com.reto.usuario.domain.exception.EmailException;
import com.reto.usuario.domain.api.IUserServicePort;
import com.reto.usuario.domain.exception.EmptyFieldsException;
import com.reto.usuario.domain.exception.InvalidCellPhoneFormatException;
import com.reto.usuario.domain.exception.RolNotFoundException;
import com.reto.usuario.domain.exception.UserNotFoundException;
import com.reto.usuario.domain.model.RolModel;
import com.reto.usuario.domain.model.UserModel;
import com.reto.usuario.domain.serviceproviderinterface.IRolPersistenceDomainPort;
import com.reto.usuario.domain.serviceproviderinterface.IUserPersistenceDomainPort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class UserUseCaseImpl implements IUserServicePort {

    private final IUserPersistenceDomainPort userPersistenceDomainPort;
    private final IRolPersistenceDomainPort rolPersistenceDomainPort;

    public UserUseCaseImpl(IUserPersistenceDomainPort userPersistenceDomainPort,
                           IRolPersistenceDomainPort rolesPersistenceDomainPort) {
        this.userPersistenceDomainPort = userPersistenceDomainPort;
        this.rolPersistenceDomainPort = rolesPersistenceDomainPort;
    }

    @Override
    public void registerUserWithOwnerRole(UserModel userModel) {
        restrictionsWhenSavingAUser(userModel);
        userModel.setPassword(PasswordEncoderUtils.passwordEncoder().encode(userModel.getPassword()));
        userModel.setRol(rolPersistenceDomainPort.findByNombre("PROPIETARIO"));
        userPersistenceDomainPort.saveUser(userModel);
    }

    @Override
    public void registerUserWithEmployeeRole(UserModel userModel) {
        restrictionsWhenSavingAUser(userModel);
        userModel.setPassword(PasswordEncoderUtils.passwordEncoder().encode(userModel.getPassword()));
        RolModel rolModel = rolPersistenceDomainPort.findByIdRol(userModel.getRol().getIdRol());
        if(rolModel == null) {
            throw new RolNotFoundException("The rol not found");
        } else if(!rolModel.getName().equals("EMPLEADO") ) {
            throw new RolNotFoundException("The rol is different from employee");
        }
        userModel.setRol(rolModel);
        userPersistenceDomainPort.saveUser(userModel);
    }

    private void restrictionsWhenSavingAUser(UserModel userModel) {
        if(userPersistenceDomainPort.existsByEmail(userModel.getEmail())) {
            throw new EmailException("The email " + userModel.getEmail()  + " already exists");
        }
        validateEmailStructure(userModel.getEmail());
        validateUserFieldsEmpty(userModel);
        validateUserCellPhone(userModel.getCellPhone());
    }

    private void validateUserFieldsEmpty(UserModel user) {
        if( user.getIdentificationDocument() == null || user.getName().replaceAll(" ", "").isEmpty() ||
                user.getLastName().replaceAll(" ", "").isEmpty() ||
                user.getCellPhone().replaceAll(" ", "").isEmpty() ||
                user.getEmail().replaceAll(" ", "").isEmpty() ||
                user.getPassword().replaceAll(" ", "").isEmpty() ) {
            throw new EmptyFieldsException("Fields cannot be empty");
        }
    }

    private void validateEmailStructure(String email) {
        int atPosition = email.lastIndexOf( '@' );
        int dotPosition = email.lastIndexOf( '.' );
        if( email.endsWith(".") || atPosition == -1 || dotPosition == -1 ||
                dotPosition < atPosition ) {
            throw new EmailException("Wrong email structure");
        }

    }

    private void validateUserCellPhone(String phoneUser) {
        String phoneUserNoSpaces = phoneUser.replaceAll(" ", "");
        if(phoneUserNoSpaces.startsWith("+")){
            if(!phoneUserNoSpaces.matches("\\+\\d+") || phoneUserNoSpaces.length() > 13 ) {
                throw new InvalidCellPhoneFormatException("The cell phone format is wrong");
            }
        } else {
            if (phoneUserNoSpaces.length() > 10 || !phoneUserNoSpaces.matches("[0-9]+")) {
                throw new InvalidCellPhoneFormatException("The cell phone format is wrong");
            }
        }
    }

    @Override
    public String signInUseCase(AuthCredentials authCredentials) {
        UserModel user = findUsuarioByEmail( authCredentials.getEmail() );
        List<String> authority = new ArrayList<>();
        authority.add("ROLE_" + user.getRol().getName());
        return TokenUtils
                .createToken( user.getEmail(), authority, user.getName(), user.getLastName() );
    }

    @Override
    public UserModel findUsuarioByEmail(String email) {
        return userPersistenceDomainPort.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("The user with the email " + email + " not found."));
    }

    @Override
    public UserModel getUserById(long idUser) {
        UserModel userModel = userPersistenceDomainPort.findById(idUser);
        if(userModel == null) {
            throw new UserNotFoundException("User not found");
        }
        return userModel;
    }
}
