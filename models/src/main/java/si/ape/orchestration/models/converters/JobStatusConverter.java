package si.ape.orchestration.models.converters;

import si.ape.orchestration.lib.JobStatus;
import si.ape.orchestration.models.entities.JobStatusEntity;

public class JobStatusConverter {

    public static JobStatus toDto(JobStatusEntity entity) {

        JobStatus dto = new JobStatus();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;

    }

    public static JobStatusEntity toEntity(JobStatus dto) {

        JobStatusEntity entity = new JobStatusEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;

    }

}
