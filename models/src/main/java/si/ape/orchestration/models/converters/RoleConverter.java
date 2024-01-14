package si.ape.orchestration.models.converters;

import si.ape.orchestration.lib.Role;
import si.ape.orchestration.models.entities.RoleEntity;

public class RoleConverter {

    public static Role toDto(RoleEntity entity) {

        Role dto = new Role();
        dto.setId(entity.getId());
        dto.setRoleName(entity.getRoleName());
        return dto;

    }

    public static RoleEntity toEntity(Role dto) {

        RoleEntity entity = new RoleEntity();
        entity.setId(dto.getId());
        entity.setRoleName(dto.getRoleName());
        return entity;

    }

}
