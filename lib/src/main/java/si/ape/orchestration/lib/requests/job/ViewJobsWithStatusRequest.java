package si.ape.orchestration.lib.requests.job;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbProperty;

public class ViewJobsWithStatusRequest {

    @Schema(description = "ID of the employee to which the job is assigned.", example = "1", required = true)
    @JsonbProperty("employeeId")
    private Integer employeeId;

    @Schema(description = "ID of the status of the job.", example = "1", required = true)
    @JsonbProperty("statusId")
    private Integer statusId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

}
