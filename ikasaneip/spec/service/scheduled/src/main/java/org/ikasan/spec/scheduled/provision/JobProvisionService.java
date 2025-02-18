package org.ikasan.spec.scheduled.provision;

import org.ikasan.spec.scheduled.job.model.SchedulerJob;

import java.util.List;

public interface JobProvisionService {

    /**
     * Service to provision scheduler jobs.
     *
     * @param jobs
     * @param actor
     */
    void provisionJobs(List<SchedulerJob> jobs, String actor);

    /**
     * Remove jobs from the agent for a given context.
     *
     * @param contextName
     */
    void removeJobs(String contextName);
}
