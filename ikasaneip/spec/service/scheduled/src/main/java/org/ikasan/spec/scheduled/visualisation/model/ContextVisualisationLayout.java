package org.ikasan.spec.scheduled.visualisation.model;

public interface ContextVisualisationLayout {

    /**
     * Generates a JSON representation of the layout.
     *
     * @return a JSON string representing the layout
     */
    String getLValayoutJson();

    /**
     * Sets the layout configuration in JSON format for the visualisation context.
     *
     * @param layoutJson a string representing the layout configuration in JSON format
     */
    void setLayoutJson(String layoutJson);
}
