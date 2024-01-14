package si.ape.orchestration.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "job_status")
@NamedQueries(value =
        {
                @NamedQuery(name = "JobStatusEntity.getAll",
                        query = "SELECT js FROM JobStatusEntity js")
        })
public class JobStatusEntity {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
