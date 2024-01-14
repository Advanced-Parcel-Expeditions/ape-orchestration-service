package si.ape.orchestration.models.converters;

import si.ape.orchestration.models.entities.EmployeeEntity;
import si.ape.orchestration.lib.Employee;

public class EmployeeConverter {

    public static Employee toDto(EmployeeEntity entity) {

        Employee dto = new Employee();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setBranch(entity.getBranch() == null ? null : BranchConverter.toDto(entity.getBranch()));
        dto.setUser(entity.getUser() == null ? null : UserConverter.toDto(entity.getUser()));
        return dto;

    }

    public static EmployeeEntity toEntity(Employee dto) {

        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setBranch(BranchConverter.toEntity(dto.getBranch()));
        entity.setUser(UserConverter.toEntity(dto.getUser()));
        return entity;

    }

}