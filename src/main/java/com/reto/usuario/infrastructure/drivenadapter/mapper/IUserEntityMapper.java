package com.reto.usuario.infrastructure.drivenadapter.mapper;

import com.reto.usuario.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.reto.usuario.infrastructure.drivenadapter.entity.UserEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserEntityMapper {

    UserEntity toUserEntity(UserModel userModel);
    UserModel toUserModel(UserEntity userEntity);
}
