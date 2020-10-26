package com.softeng306.Entities;

import java.util.ArrayList;

/**
 * Represents a main assessment component of a course.
 * This class is a subclass of {@code CourseworkComponent}.
 * A course can have many main assessment components.

 */
public class MainComponent extends CourseworkComponent {

    /**
     * This main component's sub components.
     */
    private ArrayList<SubComponent> subComponents;

    /**
     * Creates a main component with component name, component weightage and sub components.
     * @param componentName the name of the assessment component
     * @param componentWeight the componentWeight of the assessment component
     * @param subComponents the sub components of the assessment component
     */
    public MainComponent(String componentName, int componentWeight, ArrayList<SubComponent> subComponents) {
        super(componentName, componentWeight);
        this.subComponents = subComponents;
    }

    /**
     * Gets the sub components of this main component.
     * @return the sub components of this main component.
     */
    public ArrayList<SubComponent> getSubComponents() {
        return this.subComponents;
    }

    /**
     * Prints the course component type (main component or sub component).
     * Implements the abstract method {@code printComponentType} in {@code CourseworkComponent}.
     */
    public void printComponentType() {
        System.out.println("This is a main-component.");
        System.out.println("There are " + this.subComponents.size() + " sub-components inside");
    }

}
