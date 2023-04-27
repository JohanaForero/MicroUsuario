package com.reto.usuario.application.handler.implementation;

import com.reto.usuario.domain.model.UserModel;
import com.reto.usuario.domain.api.IUserServicePort;
import com.reto.usuario.application.dto.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserServicePort userServicePort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userServicePort.findUsuarioByEmail(username);
        List<GrantedAuthority> authority = new ArrayList<>();
        authority.add(new SimpleGrantedAuthority("ROLE_" + userModel.getRol().getName()));
        return new UserDetailsImpl(userModel.getEmail(), userModel.getPassword(), authority);
    }

    public boolean isValidateRoles(String email, String rol) {
        UserModel userModel = userServicePort.findUsuarioByEmail(email);
        if(userModel.getRol().getName().equals(rol)) {
            return true;
        }
        return false;
    }
}
