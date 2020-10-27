package com.softeng306.Entities;

public interface IGroup {
    /**
     * Gets the name of this group.
     *
     * @return this group's name.
     */
    String getGroupName();

    /**
     * Gets the current available vacancies for this group.
     *
     * @return this group's current available vacancy.
     */
    int getAvailableVacancies();

    /**
     * Gets the total seats for this group.
     *
     * @return this group's total seats.
     */
    int getTotalSeats();

    /**
     * Updates the available vacancies of this group after someone has registered this group.
     */
    void enrolledIn();
}
