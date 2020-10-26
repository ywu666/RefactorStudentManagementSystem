package com.softeng306;

import java.util.HashMap;

/**
 * Represents a student mark record associated with one student and a course.
 * Both students and courses can have multiple student mark record, but cannot be duplicate.
 */

public class Mark {
    /**
     * The student of this student mark record.
     */
    private Student student;
    /**
     * The course of this student mark record.
     */
    private Course course;
    /**
     * The course work marks of this student mark record.
     */
    private HashMap<CourseworkComponent, Double> courseWorkMarks;
    /**
     * The total mark of this student mark record.
     */
    private double totalMark;

    /**
     * Creates a new student mark record with the student of this student mark record, the course of this student mark record.
     * the course work marks of this student mark record, the total mark of this student mark record.
     *
     * @param student         The student of this student mark record.
     * @param course          The course of this student mark record.
     * @param courseWorkMarks The course work marks of this student mark record.
     * @param totalMark       The total mark of this student mark record.
     */
    public Mark(Student student, Course course, HashMap<CourseworkComponent, Double> courseWorkMarks, double totalMark) {
        this.student = student;
        this.course = course;
        this.courseWorkMarks = courseWorkMarks;
        this.totalMark = totalMark;
    }

    /**
     * Gets the student of this student mark record.
     *
     * @return the student of this student mark record.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Gets the course of this student mark record.
     *
     * @return the course of this student mark record.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Gets the course work marks of this student mark record.
     *
     * @return a hashmap contains the course work marks of this student mark record.
     */
    public HashMap<CourseworkComponent, Double> getCourseWorkMarks() {
        return courseWorkMarks;
    }

    /**
     * Gets the total mark of this student mark record.
     *
     * @return the total mark of this student mark record.
     */
    public double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(double totalMark){
        this.totalMark = totalMark;
    }



}


