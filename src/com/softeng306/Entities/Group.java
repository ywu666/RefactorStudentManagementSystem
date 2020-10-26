package com.softeng306.Entities;

/**
 * Represents study groups (LectureGroup, TutorialGroup and LabGroup) for a course.
 * A course must have at least one lecture group.
 * A course can have many or no tutorial groups and lab groups.
 * Student enrolled in this course must also be enrolled in one of the groups of each type.
 */

public class Group implements IGroup {
    /**
     * The name of this group.
     */
    private final String groupName;
    /**
     * The total seates of this group
     */
    private final int totalSeats;
    /**
     * The availableVacancies of this group.
     */
    private int availableVacancies;


    /**
     * Creates a group with the group name, the current available vacancy of this course, and the total seats for this group.
     *
     * @param groupName          This group's name.
     * @param availableVacancies This group's current available vacancy.
     * @param totalSeats         This group's total seats.
     */
    public Group(String groupName, int availableVacancies, int totalSeats) {
        this.groupName = groupName;
        this.availableVacancies = availableVacancies;
        this.totalSeats = totalSeats;
    }

    public String getGroupName() {
        return this.groupName;
    }


    public int getAvailableVacancies() {
        return this.availableVacancies;
    }


    public int getTotalSeats() {
        return totalSeats;
    }


    public void enrolledIn() {
        this.availableVacancies -= 1;
    }
}
