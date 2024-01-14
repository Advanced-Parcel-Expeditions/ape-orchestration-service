package si.ape.orchestration.services.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class StatisticsBean {

    @Inject
    private EntityManager em;

    public void viewStatistics() {

    }

    public void viewStatisticsOfBranch() {

    }

}
