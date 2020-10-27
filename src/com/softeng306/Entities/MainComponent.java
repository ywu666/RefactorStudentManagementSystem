package com.softeng306.Entities;

import java.util.ArrayList;

/**
 * Represents a main assessment component of a course.
 * This class is a subclass of {@code CourseworkComponent}.
 * A course can have many main assessment components.
 */
public class MainComponent implements CourseworkComponent {


    /**
     * The name of this coursework.
     */
    private final String componentName;
    /**
     * The weight of this course component.
     */
    private final int componentWeight;
    /**
     * This main component's sub components.
     */
    private final ArrayList<SubComponent> subComponents;

    /**
     * Creates a main component with component name, component weightage and sub components.
     *
     * @param componentName   the name of the assessment component
     * @param componentWeight the componentWeight of the assessment component
     * @param subComponents   the sub components of the assessment component
     */
    public MainComponent(String componentName, int componentWeight, ArrayList<SubComponent> subComponents) {
        this.componentName = componentName;
        this.componentWeight = componentWeight;
        this.subComponents = subComponents;
    }

    /**
     * Gets the component name
     *
     * @return the name of this component
     */
    public String getComponentName() {
        return this.componentName;
    }

    /**
     * Gets the weight of this component
     *
     * @return the weight of this component
     */
    public int getComponentWeight() {
        return this.componentWeight;
    }

    /**
     * Gets the sub components of this main component.
     *
     * @return the sub components of this main component.
     */
    public ArrayList<SubComponent> getSubComponents() {
        return this.subComponents;
    }


}
