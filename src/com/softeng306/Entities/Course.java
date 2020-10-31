package com.softeng306.Entities;

import java.util.ArrayList;

/**
 * This class represents a Course.
 */
public class Course {
    /**
     * The ID of this course.
     */
    private final String courseID;

    /**
     * The name of this course.
     */
    private final String courseName;

    /**
     * The AU of this course.
     */
    private final int AU;

    /**
     * The professor in charge of this course.
     */
    private final Professor profInCharge;

    /**
     * The department this course belongs to.
     */
    private final String courseDepartment;

    /**
     * The type of this course.
     */
    private final String courseType;
    /**
     * The total seats of this course.
     */
    private final int totalSeats;
    /**
     * The weekly lecture hour of this course.
     */
    private final int lecWeeklyHour;
    /**
     * The lecture groups of this course.
     */
    private final ArrayList<IGroup> lectureGroups;
    /**
     * The current vacancy of this course.
     */
    private int vacancies;
    /**
     * The weekly tutorial hour of this course.
     */
    private int tutWeeklyHour = 0;


    /**
     * The tutorial groups of this course.
     */
    private ArrayList<IGroup> tutorialGroups = new ArrayList<>(0);

    /**
     * The weekly lab hour of this course.
     */
    private int labWeeklyHour = 0;

    /**
     * The lab groups of this course.
     */
    private ArrayList<IGroup> labGroups = new ArrayList<>(0);

    /**
     * The assessment components of this course.
     */
    private ArrayList<MainComponent> mainComponents = new ArrayList<>(0);


    /**
     * Creates the course with course ID, course name, professor in charge, current vacancies, total seats lectures groups, AU, course department, course type and weekly lecture hour
     *
     * @param courseID         The ID of this course.
     * @param courseName       The name of this course.
     * @param profInCharge     The professor in charge of this course.
     * @param vacancies        The current vacancy of this course.
     * @param totalSeats       The total seats of this course.
     * @param lectureGroups    The lecture groups of this course.
     * @param AU               The AU of this course.
     * @param courseDepartment The course department of this course.
     * @param courseType       The course type of this course.
     * @param lecWeeklyHour    The lecture weekly hour of this course.
     */
    public Course(String courseID, String courseName, Professor profInCharge, int vacancies, int totalSeats, ArrayList<IGroup> lectureGroups, int AU, String courseDepartment, String courseType, int lecWeeklyHour) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.profInCharge = profInCharge;
        this.vacancies = vacancies;
        this.totalSeats = totalSeats;
        this.lectureGroups = lectureGroups;
        this.AU = AU;
        this.courseDepartment = courseDepartment;
        this.courseType = courseType;
        this.lecWeeklyHour = lecWeeklyHour;
    }

    /**
     * Creates the course with course ID, course name, professor in charge, current vacancies, total seats lectures groups, tutorial groups, lab groups, AU, course department, course type, weekly lecture hour, weekly tutorial hour, weekly lab hour
     *
     * @param courseID         The ID of this course.
     * @param courseName       The name of this course.
     * @param profInCharge     The professor in charge of this course.
     * @param vacancies        The current vacancy of this course.
     * @param totalSeats       The total seats of this course.
     * @param lectureGroups    The lecture groups of this course.
     * @param tutorialGroups   The tutorial groups of this course.
     * @param labGroups        The lab groups of this course.
     * @param AU               The AU of this course.
     * @param courseDepartment The course department of this course.
     * @param courseType       The course type of this course.
     * @param lecWeeklyHour    The lecture weekly hour of this course.
     * @param tutWeeklyHour    The tutorial weekly hour of this course.
     * @param labWeeklyHour    The lab weekly hour of this course.
     */
    public Course(String courseID, String courseName, Professor profInCharge, int vacancies, int totalSeats, ArrayList<IGroup> lectureGroups, ArrayList<IGroup> tutorialGroups, ArrayList<IGroup> labGroups, int AU, String courseDepartment, String courseType, int lecWeeklyHour, int tutWeeklyHour, int labWeeklyHour) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.profInCharge = profInCharge;
        this.vacancies = vacancies;
        this.totalSeats = totalSeats;
        this.lectureGroups = lectureGroups;
        this.tutorialGroups = tutorialGroups;
        this.labGroups = labGroups;
        this.AU = AU;
        this.courseDepartment = courseDepartment;
        this.courseType = courseType;
        this.lecWeeklyHour = lecWeeklyHour;
        this.tutWeeklyHour = tutWeeklyHour;
        this.labWeeklyHour = labWeeklyHour;
    }

    /**
     * Gets the course's ID.
     *
     * @return the ID of this course.
     */
    public String getCourseID() {
        return courseID;
    }

    /**
     * Gets the course's name.
     *
     * @return the name of this course.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Gets the course's AU.
     *
     * @return the AU of this course.
     */
    public int getAU() {
        return AU;
    }

    /**
     * Gets the course's professor in charge.
     *
     * @return the professor in charge of this course.
     */
    public Professor getProfInCharge() {
        return profInCharge;
    }

    /**
     * Gets the course's current vacancy.
     *
     * @return the current vacancy of this course.
     */
    public int getVacancies() {
        return vacancies;
    }

    /**
     * Sets the course's current current vacancy.
     *
     * @param vacancies this course's vacancy.
     */
    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    /**
     * Gets the course's total seats.
     *
     * @return the total seats of this course.
     */
    public int getTotalSeats() {
        return totalSeats;
    }

    /**
     * Gets the course's department.
     *
     * @return the department of this course.
     */
    public String getCourseDepartment() {
        return courseDepartment;
    }

    /**
     * Gets the course's type.
     *
     * @return the type of this course.
     */
    public String getCourseType() {
        return courseType;
    }

    /**
     * Gets the course's weekly lecture hour.
     *
     * @return the weekly lecture hour of this course.
     */
    public int getLecWeeklyHour() {
        return lecWeeklyHour;
    }

    /**
     * Gets the course's weekly tutorial hour.
     *
     * @return the weekly tutorial hour of this course.
     */
    public int getTutWeeklyHour() {
        return tutWeeklyHour;
    }

    /**
     * Sets the weekly hour of the tutorials.
     *
     * @param tutWeeklyHour this course's weekly tutorial hour.
     */
    public void setTutWeeklyHour(int tutWeeklyHour) {
        this.tutWeeklyHour = tutWeeklyHour;
    }

    /**
     * Gets the course's weekly lab hour.
     *
     * @return the weekly lab hour of this course.
     */
    public int getLabWeeklyHour() {
        return labWeeklyHour;
    }

    /**
     * Sets the weekly hour of the labs.
     *
     * @param labWeeklyHour this course's weekly lab hour.
     */
    public void setLabWeeklyHour(int labWeeklyHour) {
        this.labWeeklyHour = labWeeklyHour;
    }

    /**
     * Gets the course's lecture groups.
     *
     * @return the lecture groups of this course.
     */
    public ArrayList<IGroup> getLectureGroups() {
        return lectureGroups;
    }

    /**
     * Gets the course's tutorial groups
     *
     * @return the tutorial groups of this course
     */
    public ArrayList<IGroup> getTutorialGroups() {
        return this.tutorialGroups;
    }

    /**
     * Sets the tutorial groups of the lecture groups.
     *
     * @param tutorialGroups this course's tutorial groups.
     */
    public void setTutorialGroups(ArrayList<IGroup> tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }

    /**
     * Gets the course's lab groups.
     *
     * @return the lab groups of this course.
     */
    public ArrayList<IGroup> getLabGroups() {
        return this.labGroups;
    }

    /**
     * Sets the lab groups of the lecture groups.
     *
     * @param labGroups this course's lab groups.
     */
    public void setLabGroups(ArrayList<IGroup> labGroups) {
        this.labGroups = labGroups;
    }

    /**
     * Gets the course's main assessment components.
     *
     * @return the main assessment components of this course.
     */
    public ArrayList<MainComponent> getMainComponents() {
        return this.mainComponents;
    }

    /**
     * Sets the main assessment of the lecture groups.
     *
     * @param mainComponents this course's main assessment.
     */
    public void setMainComponents(ArrayList<MainComponent> mainComponents) {
        this.mainComponents = mainComponents;
    }

    /**
     * Updates the available vacancies of this course after someone has registered this group.
     */
    public void enrolledIn() {
        this.vacancies = vacancies - 1;
    }

}
