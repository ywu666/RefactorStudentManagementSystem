package com.softeng306;

public abstract class CourseworkComponent {
    /**
     * The name of this coursework.
     */
    private final String componentName;
    /**
     * The weight of this course component.
     */
    private final int componentWeight;

    /**
     * Creates a course work components with component name and component weight
     *
     * @param componentName   the name of this coursework component
     * @param componentWeight the weight of this coursework component
     */
    public CourseworkComponent(String componentName, int componentWeight) {
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

    /**
     * Prints the component type
     */
    public abstract void printComponentType();
}
