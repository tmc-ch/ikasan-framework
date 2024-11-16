package org.ikasan.spec.scheduled.visualisation.service;

import org.ikasan.spec.scheduled.visualisation.model.ContextVisualisationLayoutRecord;

import java.util.List;

public interface ContextVisualisationLayoutService {

    /**
     * Retrieves a context visualisation layout record by its unique identifier.
     *
     * @param id The unique identifier of the context visualisation layout record to retrieve.
     * @return The context visualisation layout record with the specified ID.
     */
    ContextVisualisationLayoutRecord findById(String id);

    /**
     * Retrieves a list of ContextVisualisationLayoutRecord objects by their parent context.
     *
     * @param parentContext The parent context for which to find related layout records.
     * @return A list of ContextVisualisationLayoutRecord objects that have the specified parent context.
     */
    List<ContextVisualisationLayoutRecord> findByParentContext(String parentContext);

    /**
     * Retrieves a ContextVisualisationLayoutRecord based on the parent context and context provided.
     *
     * @param parentContext The parent context of the record to search for.
     * @param context The specific context of the record to search for.
     *
     * @return The ContextVisualisationLayoutRecord that matches the parentContext and context parameters.
     */
    ContextVisualisationLayoutRecord findByParentContextAndContext(String parentContext, String context);
}
