package si.ape.orchestration.models.converters;

import si.ape.orchestration.lib.User;
import si.ape.orchestration.models.entities.UserEntity;

public class UserConverter {

    public static User toDto(UserEntity entity) {

        User dto = new User();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRole(RoleConverter.toDto(entity.getRole()));

        return dto;

    }

    public static UserEntity toEntity(User dto) {

        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole(RoleConverter.toEntity(dto.getRole()));

        return entity;

    }

}
