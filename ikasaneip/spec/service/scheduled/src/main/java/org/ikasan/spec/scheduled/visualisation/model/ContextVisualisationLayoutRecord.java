package org.ikasan.spec.scheduled.visualisation.model;

public interface ContextVisualisationLayoutRecord {

    /**
     * Retrieves the unique identifier associated with this object.
     *
     * @return The unique identifier as a String.
     */
    String getId();

    /**
     * Sets the ID of the object.
     *
     * @param id the string representing the ID to be set
     */
    void setId(String id);


    /**
     * Retrieves the parent context of this context visualisation layout record.
     *
     * @return The parent context of this context visualisation layout record.
     */
    String getParentContext();

    /**
     * Sets the parent context for the context visualization layout record.
     *
     * @param parentContext the parent context to be set for the record
     */
    void setParentContext(String parentContext);

    /**
     * Returns the context associated with this object.
     *
     * @return A string representing the context of the object.
     */
    String getContext();

    /**
     * Sets the context for the layout record.
     *
     * @param context The context to be set for the layout record.
     */
    void setContext(String context);

    /**
     * Retrieves the context visualisation layout.
     *
     * @return The context visualisation layout containing methods to get and set the layout JSON.
     */
    ContextVisualisationLayout getContextVisualisationLayout();

    /**
     * Sets the context visualisation layout for a record.
     *
     * @param contextVisualisationLayout The interface containing methods to get and set the layout JSON.
     */
    void setContextVisualisationLayout(ContextVisualisationLayout contextVisualisationLayout);

    /**
     * Retrieves the timestamp representing the current system time.
     *
     * @return A long value representing the current system time in milliseconds since the epoch (January 1, 1970, 00:00:00 GMT).
     */
    long getTimestamp();

    /**
     * Sets the timestamp for the given object.
     *
     * @param timestamp the timestamp to be set
     */
    void setTimestamp(long timestamp);

    /**
     * Retrieves the timestamp when the record was last modified.
     *
     * @return The timestamp when the record was last modified.
     */
    long getModifiedTimestamp();

    /**
     * Sets the modified timestamp for the ScheduledContextViewRecord.
     *
     * @param timestamp The new modified timestamp.
     */
    void setModifiedTimestamp(long timestamp);

    /**
     * Retrieves the username of the person who last modified the record.
     *
     * @return The username of the person who last modified the record.
     */
    String getModifiedBy();

    /**
     * Sets the username of the user who last modified the record.
     *
     * @param modifiedBy the username of the user who last modified the record
     */
    void setModifiedBy(String modifiedBy);
}
