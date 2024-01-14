package si.ape.orchestration.services.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class JobBean {

    @Inject
    private EntityManager em;

    public void viewJobs() {

    }

    public void viewJobsWithStatus() {

    }

    public void viewJobsOfBranch() {

    }

    public void createJob() {

    }

    public void completeJob() {

    }

    public void cancelJob() {

    }

}
