package si.ape.orchestration.lib.requests.job;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbProperty;

public class CompleteJobRequest {

    @Schema(description = "Job ID", required = true)
    @JsonbProperty("jobId")
    private Integer jobId;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

}
