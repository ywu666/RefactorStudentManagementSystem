package com.softeng306.Entities;

/**
 * Represents a sub-component of a main component.
 * A main component can have many or no sub-components.
 * This class extends {@code CourseWorkComponent}.

 *
 */
public class SubComponent implements CourseworkComponent {

    /**
     * The name of this coursework.
     */
    private final String componentName;
    /**
     * The weight of this course component.
     */
    private final int componentWeight;
    /**
     * Creates a sub-component with this sub-component's name and this sub-component's weightage.
     * This function makes use of the interface {@code CourseWorkComponent}.
     * @param componentName This sub-component's name.
     * @param componentWeight This sub-component's weightage.
     */
    public SubComponent(String componentName, int componentWeight) {
        this.componentName = componentName;
        this.componentWeight = componentWeight;
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

}
