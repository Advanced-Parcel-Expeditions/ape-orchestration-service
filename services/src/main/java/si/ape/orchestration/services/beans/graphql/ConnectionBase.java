package si.ape.orchestration.services.beans.graphql;

import java.util.List;

public class ConnectionBase<T> {

    private List<T> edges;

    private long totalCount;

    public ConnectionBase() {
        this.edges = null;
        this.totalCount = 0;
    }

    public ConnectionBase(List<T> edges, long totalCount) {
        this.edges = edges;
        this.totalCount = totalCount;
    }

    public List<T> getEdges() {
        return edges;
    }

    public void setEdges(List<T> edges) {
        this.edges = edges;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

}
