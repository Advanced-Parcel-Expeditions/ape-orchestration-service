package si.ape.orchestration.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "job_type")
@NamedQueries(value =
        {
                @NamedQuery(name = "JobTypeEntity.getAll",
                        query = "SELECT jt FROM JobTypeEntity jt")
        })
public class JobTypeEntity {

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
