package si.ape.orchestration.services.beans.graphql;

import si.ape.orchestration.lib.Job;

import java.util.List;

public class JobConnection extends ConnectionBase<Job> {

    public JobConnection() {
        super();
    }

    public JobConnection(List<Job> edges, long totalCount) {
        super(edges, totalCount);
    }

}
