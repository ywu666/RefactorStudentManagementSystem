package com.softeng306.Entities;

import java.util.Comparator;

/**
 * This class represents the registration of one student into one course.
 */
public class CourseRegistration {
    public static Comparator<CourseRegistration> LecComparator = new Comparator<CourseRegistration>() {
        @Override
        public int compare(CourseRegistration o1, CourseRegistration o2) {
            String group1 = o1.getLectureGroup().toUpperCase();
            String group2 = o2.getLectureGroup().toUpperCase();

            //ascending order
            return group1.compareTo(group2);

        }
    };
    public static Comparator<CourseRegistration> TutComparator = new Comparator<CourseRegistration>() {
        @Override
        public int compare(CourseRegistration s1, CourseRegistration s2) {
            String group1 = s1.getTutorialGroup().toUpperCase();
            String group2 = s2.getTutorialGroup().toUpperCase();

            //ascending order
            return group1.compareTo(group2);

        }
    };
    public static Comparator<CourseRegistration> LabComparator = new Comparator<CourseRegistration>() {

        @Override
        public int compare(CourseRegistration o1, CourseRegistration o2) {
            String group1 = o1.getLabGroup().toUpperCase();
            String group2 = o2.getLabGroup().toUpperCase();

            //ascending order
            return group1.compareTo(group2);
        }

    };
    private final Student student;
    private final Course course;
    private final String lectureGroup;
    private final String tutorialGroup;
    private final String labGroup;

    /**
     * Creates the CourseRegistration with a student, course, lecture, lab, and tutorial groups.
     * @param student           The student being registered.
     * @param course            The course being registered in.
     * @param lectureGroup      The student's lecture group enrolment.
     * @param tutorialGroup     The student's tutorial group enrolment.
     * @param labGroup          The student's lab group enrolment.
     */
    public CourseRegistration(Student student, Course course, String lectureGroup, String tutorialGroup, String labGroup) {
        this.student = student;
        this.course = course;
        this.lectureGroup = lectureGroup;
        this.tutorialGroup = tutorialGroup;
        this.labGroup = labGroup;
    }

    /**
     * Gets the student being registered.
     *
     * @return registered Student.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Course the registration is for.
     *
     * @return this registration's Course.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Get the lecture group associated with this registration.
     *
     * @return String representation of lecture group.
     */
    public String getLectureGroup() {
        return lectureGroup;
    }

    /**
     * Get the tutorial group associated with this registration.
     *
     * @return String representation of tutorial group.
     */
    public String getTutorialGroup() {
        return tutorialGroup;
    }

    /**
     * Get the lab group associated with this registration.
     *
     * @return String representation of lab group.
     */
    public String getLabGroup() {
        return labGroup;
    }
}
