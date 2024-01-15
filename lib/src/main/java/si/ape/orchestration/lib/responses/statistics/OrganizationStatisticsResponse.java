package si.ape.orchestration.lib.responses.statistics;

import java.util.ArrayList;
import java.util.List;

public class OrganizationStatisticsResponse {

    private Long numberOfServicePoints;

    private Long numberOfParcelCenters;

    private Long numberOfBranchOffices;

    private Long numberOfEmployees;

    private Long numberOfParcels;

    private List<BranchStatisticsResponse> branchStatisticsResponses = new ArrayList<>();

    public Long getNumberOfServicePoints() {
        return numberOfServicePoints;
    }

    public void setNumberOfServicePoints(Long numberOfServicePoints) {
        this.numberOfServicePoints = numberOfServicePoints;
    }

    public Long getNumberOfParcelCenters() {
        return numberOfParcelCenters;
    }

    public void setNumberOfParcelCenters(Long numberOfParcelCenters) {
        this.numberOfParcelCenters = numberOfParcelCenters;
    }

    public Long getNumberOfBranchOffices() {
        return numberOfBranchOffices;
    }

    public void setNumberOfBranchOffices(Long numberOfBranchOffices) {
        this.numberOfBranchOffices = numberOfBranchOffices;
    }

    public Long getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(Long numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public Long getNumberOfParcels() {
        return numberOfParcels;
    }

    public void setNumberOfParcels(Long numberOfParcels) {
        this.numberOfParcels = numberOfParcels;
    }

    public List<BranchStatisticsResponse> getBranchStatisticsResponses() {
        return branchStatisticsResponses;
    }

    public void setBranchStatisticsResponses(List<BranchStatisticsResponse> branchStatisticsResponses) {
        this.branchStatisticsResponses = branchStatisticsResponses;
    }

}