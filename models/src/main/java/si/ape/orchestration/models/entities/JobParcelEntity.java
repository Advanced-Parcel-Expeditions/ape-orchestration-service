package si.ape.orchestration.models.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "job_packet")
@NamedQueries(value =
        {
                @NamedQuery(name = "JobParcelEntity.getAll",
                        query = "SELECT jp FROM JobParcelEntity jp"),
                @NamedQuery(name = "JobParcelEntity.getByParcelId",
                        query = "SELECT jp FROM JobParcelEntity jp WHERE jp.parcel.id = :parcelId"),
                @NamedQuery(name = "JobParcelEntity.getByJobId",
                        query = "SELECT jp FROM JobParcelEntity jp WHERE jp.job.id = :jobId"),
                @NamedQuery(name = "JobParcelEntity.getParcelByJobId",
                        query = "SELECT jp.parcel FROM JobParcelEntity jp WHERE jp.job.id = :jobId"),
        })
@IdClass(JobParcelEntity.JobParcelId.class)
public class JobParcelEntity {

    @Id
    private JobEntity job;

    @Id
    private ParcelEntity parcel;

    public static class JobParcelId implements Serializable {

        /** The Job ID. */
        private Integer job;

        /** The Parcel ID. */
        private String parcel;

        public JobParcelId(Integer job, String parcel) {
            this.job = job;
            this.parcel = parcel;
        }

        public Integer getJob() {
            return job;
        }

        public void setJob(Integer job) {
            this.job = job;
        }

        public String getParcel() {
            return parcel;
        }

        public void setParcel(String parcel) {
            this.parcel = parcel;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof JobParcelId)) return false;
            JobParcelId that = (JobParcelId) o;
            return job.equals(that.job) &&
                    parcel.equals(that.parcel);
        }

        @Override
        public int hashCode() {
            return Objects.hash(job, parcel);
        }

    }

    public JobEntity getJob() {
        return job;
    }

    public void setJob(JobEntity job) {
        this.job = job;
    }

    public ParcelEntity getParcel() {
        return parcel;
    }

    public void setParcel(ParcelEntity parcel) {
        this.parcel = parcel;
    }

}
