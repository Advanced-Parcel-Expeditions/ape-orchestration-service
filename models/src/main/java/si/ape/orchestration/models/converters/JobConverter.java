package si.ape.orchestration.models.converters;

import si.ape.orchestration.lib.Job;
import si.ape.orchestration.models.entities.JobEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class JobConverter {

    public static Job toDto(JobEntity entity) {

        Job dto = new Job();
        dto.setId(entity.getId());
        dto.setDateCreated(LocalDateTime.ofInstant(entity.getDateCreated(), ZoneId.systemDefault()));
        if (entity.getDateCompleted() != null) {
            dto.setDateCompleted(LocalDateTime.ofInstant(entity.getDateCompleted(), ZoneId.systemDefault()));
        } else {
            dto.setDateCompleted(null);
        }
        dto.setJobType(JobTypeConverter.toDto(entity.getJobType()));
        dto.setJobStatus(JobStatusConverter.toDto(entity.getJobStatus()));
        dto.setStaff(EmployeeConverter.toDto(entity.getStaff()));
        return dto;

    }

    public static JobEntity toEntity(Job dto) {

        JobEntity entity = new JobEntity();
        entity.setId(dto.getId());
        entity.setDateCreated(Instant.from(dto.getDateCreated()));
        entity.setDateCompleted(Instant.from(dto.getDateCompleted()));
        entity.setJobType(JobTypeConverter.toEntity(dto.getJobType()));
        entity.setJobStatus(JobStatusConverter.toEntity(dto.getJobStatus()));
        entity.setStaff(EmployeeConverter.toEntity(dto.getStaff()));
        return entity;

    }

}
