package si.ape.orchestration.services.beans.graphql;

import si.ape.orchestration.lib.Parcel;

import java.util.List;

public class ParcelConnection extends ConnectionBase<Parcel> {

    public ParcelConnection() {
        super();
    }

    public ParcelConnection(List<Parcel> edges, long totalCount) {
        super(edges, totalCount);
    }

}
