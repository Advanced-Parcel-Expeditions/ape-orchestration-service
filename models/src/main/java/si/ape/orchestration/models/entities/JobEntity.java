package si.ape.orchestration.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "job")
@NamedQueries(value =
        {
                @NamedQuery(name = "JobEntity.getAll",
                        query = "SELECT j FROM JobEntity j"),
                @NamedQuery(name = "JobEntity.getJobsOfEmployee",
                        query = "SELECT j FROM JobEntity j WHERE j.staff.id = :employeeId"),
                @NamedQuery(name = "JobEntity.getJobsOfEmployeeWithStatus",
                        query = "SELECT j FROM JobEntity j WHERE j.staff.id = :employeeId AND j.jobStatus.id = :status")
        })
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_completed")
    private Instant dateCompleted;

    @ManyToOne
    @JoinColumn(name = "job_type_id", referencedColumnName = "id")
    private JobTypeEntity jobType;

    @ManyToOne
    @JoinColumn(name = "job_status_id", referencedColumnName = "id")
    private JobStatusEntity jobStatus;

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private EmployeeEntity staff;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Instant dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public JobTypeEntity getJobType() {
        return jobType;
    }

    public void setJobType(JobTypeEntity jobType) {
        this.jobType = jobType;
    }

    public JobStatusEntity getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatusEntity jobStatus) {
        this.jobStatus = jobStatus;
    }

    public EmployeeEntity getStaff() {
        return staff;
    }

    public void setStaff(EmployeeEntity staff) {
        this.staff = staff;
    }

}
