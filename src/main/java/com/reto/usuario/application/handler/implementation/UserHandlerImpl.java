package com.reto.usuario.application.handler.implementation;


import com.reto.usuario.application.dto.request.AuthCredentialsRequest;
import com.reto.usuario.application.dto.request.UserRequestCustomerDto;
import com.reto.usuario.application.dto.request.UserRequestDto;
import com.reto.usuario.application.dto.request.UserRequestOwnerDto;
import com.reto.usuario.application.dto.response.UserResponseDto;
import com.reto.usuario.application.mapper.IAuthCredentialsRequestMapper;
import com.reto.usuario.application.mapper.IUserRequestMapper;
import com.reto.usuario.application.handler.IUserHandler;
import com.reto.usuario.domain.model.RolModel;
import com.reto.usuario.domain.model.UserModel;
import com.reto.usuario.domain.api.IUserServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class UserHandlerImpl implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IAuthCredentialsRequestMapper authCredentialsRequestMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public void registerUserWithOwnerRole(UserRequestDto userRequestDto) {
        UserModel userModel = userRequestMapper.toUserModel(userRequestDto);
        userServicePort.registerUserWithOwnerRole(userModel);
    }

    @Override
    public void registerUserWithEmployeeRole(UserRequestOwnerDto userRequestOwnerDto) {
        RolModel rolModel = new RolModel();
        rolModel.setIdRol(userRequestOwnerDto.getIdRol());
        UserModel userModel = new UserModel();
        userModel.setName(userRequestOwnerDto.getName());
        userModel.setLastName(userRequestOwnerDto.getLastName());
        userModel.setCellPhone(userRequestOwnerDto.getCellPhone());
        userModel.setEmail(userRequestOwnerDto.getEmail());
        userModel.setPassword(userRequestOwnerDto.getPassword());
        userModel.setIdentificationDocument(userRequestOwnerDto.getIdentificationDocument());
        userModel.setRol(rolModel);
        userServicePort.registerUserWithEmployeeRole(userModel);
    }

    @Override
    public void registerUserWithCustomerRole(UserRequestCustomerDto userRequestCustomerDto) {
        RolModel rolModel = new RolModel();
        rolModel.setIdRol(userRequestCustomerDto.getIdRol());
        UserModel userModel = new UserModel();
        userModel.setName(userRequestCustomerDto.getName());
        userModel.setLastName(userRequestCustomerDto.getLastName());
        userModel.setCellPhone(userRequestCustomerDto.getCellPhone());
        userModel.setEmail(userRequestCustomerDto.getEmail());
        userModel.setPassword(userRequestCustomerDto.getPassword());
        userModel.setIdentificationDocument(userRequestCustomerDto.getIdentificationDocument());
        userModel.setRol(rolModel);
        userServicePort.registerUserWithCustomerRole(userModel);
    }

    @Override
    public String singIn(AuthCredentialsRequest authCredentialsRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authCredentialsRequest.getEmail(), authCredentialsRequest.getPassword()));

        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        return userServicePort.signInUseCase(
                authCredentialsRequestMapper.toAuthCredentials(authCredentialsRequest));
    }

    @Override
    public UserResponseDto getUserById(long idUser) {
        UserModel userModel = userServicePort.getUserById(idUser);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(userModel.getName());
        userResponseDto.setLastName(userModel.getLastName());
        userResponseDto.setRol(userModel.getRol().getName());
        return userResponseDto;
    }
}
