package com.softeng306.Entities;

/**
 * Represents a student enrolled in a school.
 * A student has studentID, studentName, studentSchool, gender, GPA and year.
 * A student can enroll many courses.
 */

public class Student {

    /**
     * The ID of this student.
     */
    private final String studentID;

    /**
     * The name of this student.
     */
    private final String studentName;

    /**
     * The school this student belongs to.
     */
    private String studentSchool;

    /**
     * The gender of this student.
     */
    private String gender;

    /**
     * The GPA of this student.
     */
    private double GPA = 0;

    /**
     * The study year of this student.
     */
    private int studentYear;

    /**
     * Creates student with the student name and student's ID.
     *
     * @param studentID   This student's name.
     * @param studentName This student's ID.
     */
    public Student(String studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

    /**
     * Gets the student's ID.
     *
     * @return this student's ID.
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Gets the student's name.
     *
     * @return this student's name.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Gets the student's school.
     *
     * @return this student's school.
     */
    public String getStudentSchool() {
        return studentSchool;
    }

    /**
     * Sets the school of this student.
     *
     * @param studentSchool this student's school.
     */
    public void setStudentSchool(String studentSchool) {
        this.studentSchool = studentSchool;
    }

    /**
     * Gets the student's gender.
     *
     * @return this student's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of this student.
     *
     * @param gender this student's gender.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the student's GPA.
     *
     * @return this student's GPA.
     */
    public double getGPA() {
        return GPA;
    }

    /**
     * Sets the GPA of this student.
     *
     * @param GPA this student's GPA.
     */
    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    /**
     * Gets the student's year.
     *
     * @return this student's year.
     */
    public int getStudentYear() {
        return studentYear;
    }

    /**
     * Sets the year of this student.
     *
     * @param studentYear this student's year.
     */
    public void setStudentYear(int studentYear) {
        this.studentYear = studentYear;
    }


}
