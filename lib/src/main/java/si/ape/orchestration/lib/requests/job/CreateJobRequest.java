package si.ape.orchestration.lib.requests.job;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import si.ape.orchestration.lib.requests.parcels.CreateParcelRequest;

import javax.json.bind.annotation.JsonbProperty;

public class CreateJobRequest {

    @Schema(description = "ID of the employee to which the job is assigned.", example = "1", required = true)
    @JsonbProperty("employeeId")
    private Integer employeeId;

    @Schema(description = "Details about the parcel.", required = true)
    @JsonbProperty("parcel")
    private CreateParcelRequest parcel;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public CreateParcelRequest getParcel() {
        return parcel;
    }

    public void setParcel(CreateParcelRequest parcel) {
        this.parcel = parcel;
    }

}
