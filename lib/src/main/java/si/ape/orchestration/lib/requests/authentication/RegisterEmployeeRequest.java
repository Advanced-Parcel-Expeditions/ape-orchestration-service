package si.ape.orchestration.lib.requests.authentication;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbProperty;

public class RegisterEmployeeRequest {

    // User data.

    @Schema(description = "Username of the user.", example = "janez", required = true)
    @JsonbProperty("username")
    private String username;

    @Schema(description = "Password of the user.", example = "janez", required = true)
    @JsonbProperty("password")
    private String password;

    // Employee data.

    @Schema(description = "First name of the employee.", example = "Janez", required = true)
    @JsonbProperty("firstName")
    private String firstName;

    @Schema(description = "Last name of the employee.", example = "Novak", required = true)
    @JsonbProperty("lastName")
    private String lastName;

    @Schema(description = "ID of the employee's role.", example = "1", required = true)
    @JsonbProperty("roleId")
    private Integer roleId;

    @Schema(description = "ID of the employee's branch.", example = "12", required = true)
    @JsonbProperty("branchId")
    private Integer branchId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

}
