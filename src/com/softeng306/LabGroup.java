package com.softeng306;

/**
 * Represents a lab group for a course.
 * A course can have many lab groups.
 * This class is a subclass of {@code Group}.

 */

public class LabGroup extends Group{


    /**
     * Creates a lab group with the group name, the current available vacancy of this course, and the total seats for this group.
     * This function makes use of its super class.
     * @param groupName This lab group's name.
     * @param availableVacancies This lab group's current available vacancy.
     * @param totalSeats This lab group's total seats.
     */
    public LabGroup(String groupName, int availableVacancies, int totalSeats) {
        super(groupName, availableVacancies, totalSeats);
    }
}
