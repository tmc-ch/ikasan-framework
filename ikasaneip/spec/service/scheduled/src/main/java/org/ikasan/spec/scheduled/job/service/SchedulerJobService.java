package org.ikasan.spec.scheduled.job.service;

import org.ikasan.spec.scheduled.context.model.ContextTemplate;
import org.ikasan.spec.scheduled.job.model.*;
import org.ikasan.spec.search.SearchResults;

import java.util.List;
import java.util.Map;

public interface SchedulerJobService<T extends SchedulerJobRecord> {

    /**
     * Find jobs that are associated with an agent.
     *
     * @param agent
     * @param limit
     * @param offset
     * @return
     */
    SearchResults<? extends T> findByAgent(String agent, int limit, int offset);

    /**
     *  Find a job by its ID.
     *
     * @param id
     * @return
     */
    T findById(String id);

    /**
     * Find a job by context and job name.
     *
     * @param contextName
     * @param jobName
     * @return
     */
    T findByContextNameAndJobName(String contextName, String jobName);

    /**
     * Find jobs by context.
     *
     * @param contextId
     * @param limit
     * @param offset
     * @return
     */
    SearchResults<? extends T> findByContext(String contextId, int limit, int offset);

    /**
     * Find jobs by filter.
     *
     * @param filter
     * @param limit
     * @param offset
     * @param sortColumn
     * @param sortDirection
     * @return
     */
    SearchResults<? extends T> findByFilter(SchedulerJobSearchFilter filter, int limit, int offset, String sortColumn, String sortDirection);

    /**
     * Delete an individual job record.
     *
     * @param record
     */
    void delete(T record);

    /**
     * Delete all jobs associated with an agent
     *
     * @param agentName
     */
    void deleteByAgentName(String agentName);

    /**
     * Delete all jobs associated with a context
     *
     * @param contextName
     */
    void deleteByContextName(String contextName);

    /**
     * Save a list of jobs
     *
     * @param records
     */
    void save(List<SchedulerJob> records, String actor);

    /**
     * Save a FileEventDrivenJobRecord
     * @param fileEventDrivenJobRecord
     */
    void saveFileEventDrivenJobRecord(FileEventDrivenJobRecord fileEventDrivenJobRecord);

    /**
     * Save a InternalEventDrivenJobRecord
     *
     * @param internalEventDrivenJobRecord
     */
    void saveInternalEventDrivenJobRecord(InternalEventDrivenJobRecord internalEventDrivenJobRecord);

    /**
     * Save a QuartzScheduleDrivenJobRecord
     *
     * @param quartzScheduleDrivenJobRecord
     */
    void saveQuartzScheduledJobRecord(QuartzScheduleDrivenJobRecord quartzScheduleDrivenJobRecord);

    /**
     * Save a GlobalEventJobRecord
     *
     * @param globalEventJobRecord
     */
    void saveGlobalEventJobRecord(GlobalEventJobRecord globalEventJobRecord);

    /**
     * Save a ContextStartJobRecord
     *
     * @param contextStartJobRecord
     */
    void saveContextStartJobRecord(ContextStartJobRecord contextStartJobRecord);

    /**
     * Save a ContextTerminalJobRecord
     *
     * @param contextTerminalJobRecord
     */
    void saveContextTerminalJobRecord(ContextTerminalJobRecord contextTerminalJobRecord);


    /**
     * Saves the internal event-driven job template record with the provided modifications.
     *
     * @param internalEventDrivenJobRecord The internal event-driven job record to be saved
     * @param modifiedBy The user who modified the record
     */
    void saveInternalEventDrivenJobTemplateRecord(InternalEventDrivenJobRecord internalEventDrivenJobRecord, String modifiedBy);

    /**
     * Save a FileEventDrivenJob
     *
     * @param fileEventDrivenJob
     */
    void saveFileEventDrivenJob(FileEventDrivenJob fileEventDrivenJob, String modifiedBy);

    /**
     * Save a InternalEventDrivenJob
     *
     * @param internalEventDrivenJob
     */
    void saveInternalEventDrivenJob(InternalEventDrivenJob internalEventDrivenJob, String modifiedBy);

    /**
     * Save a QuartzScheduleDrivenJob
     *
     * @param quartzScheduleDrivenJob
     */
    void saveQuartzScheduledJob(QuartzScheduleDrivenJob quartzScheduleDrivenJob, String modifiedBy);

    /**
     * Save a GlobalEventJob
     *
     * @param globalEventJob
     */
    void saveGlobalEventJob(GlobalEventJob globalEventJob, String modifiedBy);

    /**
     * Save a ContextStartJob
     *
     * @param contextStartJob
     */
    void saveContextStartJob(ContextStartJob contextStartJob, String modifiedBy);

    /**
     * Save a ContextTerminalJob
     *
     * @param contextTerminalJob
     */
    void saveContextTerminalJob(ContextTerminalJob contextTerminalJob, String modifiedBy);


    /**
     * Saves the internal event-driven job template with the specified modifications made by the given user.
     *
     * @param internalEventDrivenJob The internal event-driven job template to be saved.
     * @param modifiedBy The user who has modified the internal event-driven job template.
     */
    void saveInternalEventDrivenJobTemplate(InternalEventDrivenJob internalEventDrivenJob, String modifiedBy);

    /**
     * Save a list of FileEventDrivenJobRecord.
     *
     * @param fileEventDrivenJobRecords
     */
    public void saveFileEventDrivenJobRecords(List<FileEventDrivenJobRecord> fileEventDrivenJobRecords);

    /**
     * Saves the provided list of InternalEventDrivenJob templates in the database, associating them with the specified actor.
     *
     * @param quartzScheduleDrivenJobs A List of InternalEventDrivenJob objects representing the job templates to be saved.
     * @param actor The name of the actor associated with the job templates.
     */
    public void saveInternalEventDrivenJobTemplates(List<InternalEventDrivenJob> quartzScheduleDrivenJobs, String actor);

    /**
     * Save a list of InternalEventDrivenJobTemplateRecord
     *
     * @param internalEventDrivenJobRecord
     */
    public void saveInternalEventDrivenJobTemplateRecords(List<InternalEventDrivenJobRecord> internalEventDrivenJobRecord);

    /**
     * Save a list of InternalEventDrivenJobRecord
     *
     * @param internalEventDrivenJobRecord
     */
    public void saveInternalEventDrivenJobRecords(List<InternalEventDrivenJobRecord> internalEventDrivenJobRecord);

    /**
     * Save a list of QuartzScheduleDrivenJobRecord.
     *
     * @param quartzScheduleDrivenJobRecord
     */
    public void saveQuartzScheduledJobRecords(List<QuartzScheduleDrivenJobRecord> quartzScheduleDrivenJobRecord);

    /**
     * Save a list of GlobalEventJobRecord.
     *
     * @param globalEventJobRecords
     */
    public void saveGlobalEventJobRecords(List<GlobalEventJobRecord> globalEventJobRecords);

    /**
     * Save a list of ContextStartJobRecord.
     *
     * @param contextStartJobRecords
     */
    public void saveContextStartJobRecords(List<ContextStartJobRecord> contextStartJobRecords);

    /**
     * Save a list of ContextTerminalJobRecord.
     *
     * @param contextTerminalJobRecords
     */
    public void saveContextTerminalJobRecord(List<ContextTerminalJobRecord> contextTerminalJobRecords);

    /**
     * Save a list of InternalEventDrivenJob.
     *
     * @param quartzScheduleDrivenJobs
     * @param actor
     */
    public void saveInternalEventDrivenJobs(List<InternalEventDrivenJob> quartzScheduleDrivenJobs, String actor);

    /**
     * Save a list of QuartzScheduleDrivenJob.
     *
     * @param quartzScheduleDrivenJobs
     * @param actor
     */
    public void saveQuartzScheduledJobs(List<QuartzScheduleDrivenJob> quartzScheduleDrivenJobs, String actor);

    /**
     * Save a list of FileEventDrivenJob.
     *
     * @param quartzScheduleDrivenJobs
     * @param actor
     */
    public void saveFileEventDrivenJobs(List<FileEventDrivenJob> quartzScheduleDrivenJobs, String actor);

    /**
     * Save a list of GlobalEventJob.
     *
     * @param globalEventJobs
     * @param actor
     */
    public void saveGlobalEventJobs(List<GlobalEventJob> globalEventJobs, String actor);

    /**
     * Save a list of ContextStartJob.
     *
     * @param contextStartJobs
     * @param actor
     */
    public void saveContextStartJobs(List<ContextStartJob> contextStartJobs, String actor);

    /**
     * Save a list of ContextTerminalJob.
     *
     * @param contextTerminalJobs
     * @param actor
     */
    public void saveContextTerminalJobs(List<ContextTerminalJob> contextTerminalJobs, String actor);

    /**
     * Set a InternalEventDrivenJobRecord to skip. If targetResidingContextOnly is set
     * on the InternalEventDrivenJob the childContextNames contain the specific child
     * contexts that the job will be skipped in.
     *
     * @param jobRecord
     * @param childContextNames
     * @param actor
     */
    void skip(T jobRecord, List<String> childContextNames, String actor);

    /**
     * Set a InternalEventDrivenJobRecord to enabled. Enabled is skipped == false.
     *
     * @param jobRecord
     * @param contextTemplateName
     * @param actor
     */
    void enable(T jobRecord, String contextTemplateName,  String actor);

    /**
     * Set a InternalEventDrivenJobRecord to hold. If targetResidingContextOnly is set
     * on the InternalEventDrivenJob the childContextNames contain the specific child
     * contexts that the job will be held in.
     *
     * @param jobRecord
     * @param childContextNames
     * @param actor
     */
    void hold(T jobRecord, List<String> childContextNames, String actor);

    /**
     * Set a InternalEventDrivenJobRecord to release. Release is held == false.
     *
     * @param jobRecord
     * @param actor
     */
    void release(T jobRecord, String actor);

    /**
     * Release all jobs that are held.
     *
     * @param contextName
     * @param actor
     */
    void releaseAll(String contextName, String actor);

    /**
     * Hold all jobs.
     *
     * @param contextName
     * @param actor
     */
    void holdAll(String contextName, String actor);

    /**
     * Enable all jobs that are skipped.
     *
     * @param contextName
     * @param actor
     */
    void enableAll(String contextName, String actor);

    /**
     * Rename the context associated with all jobs.
     *
     * @param oldName
     * @param newName
     * @param actor
     */
    void renameContextForJobs(String oldName, String newName, String actor);

    /**
     * Helper method to get all command execution jobs associated with an context instance keyed on job identifier
     * and child context name.
     *
     * The results are key on job.getIdentifier()
     *
     * @param contextName the name of the context that we want the jobs for.
     *
     * @return Map&lt;String, InternalEventDrivenJobInstance&gt; containing the command execution jobs
     * keyed on their identifier.
     */
    Map<String, InternalEventDrivenJob> getCommandExecutionJobsForContext(String contextName);
}
