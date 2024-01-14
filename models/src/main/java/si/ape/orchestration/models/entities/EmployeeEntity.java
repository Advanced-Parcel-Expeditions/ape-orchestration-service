package si.ape.orchestration.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "staff")
@NamedQueries(value =
        {
                @NamedQuery(name = "EmployeeEntity.getAll",
                        query = "SELECT e FROM EmployeeEntity e"),
                @NamedQuery(name = "EmployeeEntity.getEmployee",
                        query = "SELECT e FROM EmployeeEntity e WHERE e.id = :employeeId"),
                @NamedQuery(name = "EmployeeEntity.getAllAtBranch",
                        query = "SELECT e FROM EmployeeEntity e WHERE e.branch.id = :branchId"),
                @NamedQuery(name = "EmployeeEntity.getAllAtBranchWithRole",
                        query = "SELECT e FROM EmployeeEntity e WHERE e.branch.id = :branchId AND e.user.role.id = :roleId")
        })
public class EmployeeEntity {

    @Id
    private Integer id;

    private String name;

    private String surname;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private BranchEntity branch;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public BranchEntity getBranch() {
        return branch;
    }

    public void setBranch(BranchEntity branch) {
        this.branch = branch;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}