package com.reto.usuario.domain.serviceproviderinterface;

import com.reto.usuario.domain.model.RolModel;

public interface IRolPersistenceDomainPort {

    RolModel findByNombre(String nombre);

    RolModel findByIdRol(long idRol);
}
