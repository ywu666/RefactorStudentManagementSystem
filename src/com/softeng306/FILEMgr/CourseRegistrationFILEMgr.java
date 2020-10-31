package com.softeng306.FILEMgr;

import com.softeng306.Entities.Course;
import com.softeng306.Entities.CourseRegistration;
import com.softeng306.Entities.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * This class deals with loading information from and writing information to the Course Registration file.
 */
public class CourseRegistrationFILEMgr extends FILEMgr<CourseRegistration> {
    /**
     * The index of studentID in courseRegistrationFile.csv.
     */
    private static final int studentIdInRegistrationIndex = 0;

    /**
     * The index of courseID in courseRegistrationFile.csv.
     */
    private static final int courseIdInRegistrationIndex = 1;

    /**
     * The index of lectureGroup in courseRegistrationFile.csv.
     */
    private static final int lectureGroupInRegistrationIndex = 2;

    /**
     * The index of tutorialGroup in courseRegistrationFile.csv.
     */
    private static final int tutorialGroupInRegistrationIndex = 3;

    /**
     * The index of labGroup in courseRegistrationFile.csv.
     */
    private static final int labGroupInRegistrationIndex = 4;

    /**
     * The file name of courseRegistrationFile.csv.
     */
    private static final String courseRegistrationFileName = "data/courseRegistrationFile.csv";

    /**
     * The header of courseRegistrationFile.csv.
     */
    private static final String courseRegistration_HEADER = "studentID,courseID,lectureGroup,tutorialGroup,labGroup";

    /**
     * This method is intended to write a single registration to the course registration file.
     * @param courseRegistration CourseRegistration object to write to file.
     */
    @Override
    public void writeIntoFile(CourseRegistration courseRegistration) {
        FileWriter fileWriter = null;
        try {
            fileWriter = initialiseFileWriter(courseRegistrationFileName, courseRegistration_HEADER);

            fileWriter.append(courseRegistration.getStudent().getStudentID());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(courseRegistration.getCourse().getCourseID());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(courseRegistration.getLectureGroup());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(courseRegistration.getTutorialGroup());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(courseRegistration.getLabGroup());
            fileWriter.append(NEW_LINE_SEPARATOR);
        } catch (Exception e) {
            System.out.println("Error in adding a course registration to the file.");
            e.printStackTrace();
        } finally {
            printFinallyBlock(fileWriter);
        }

    }

    /**
     * This method is intended to load all the information from the Course Registration file into the system, and correctly
     * load this information as Course Registration objects.
     * @return A list of the generated CourseRegistration objects.
     */
    @Override
    public List<CourseRegistration> loadFromFile() {
        BufferedReader fileReader = null;
        ArrayList<CourseRegistration> courseRegistrations = new ArrayList<CourseRegistration>(0);
        try {
            String line;
            Student currentStudent = null;
            Course currentCourse = null;

            StudentFILEMgr studentFILEMgr = new StudentFILEMgr();
            List<Student> students = studentFILEMgr.loadFromFile();

            fileReader = new BufferedReader(new FileReader(courseRegistrationFileName));
            fileReader.readLine();//read the header to skip it

            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0) {
                    String studentID = tokens[studentIdInRegistrationIndex];

                    for (Student student : students) {
                        if (student.getStudentID().equals(studentID)) {
                            currentStudent = student;
                            break;
                        }
                    }
                    String courseID = tokens[courseIdInRegistrationIndex];

                    CourseFILEMgr courseFILEMgr = new CourseFILEMgr();
                    List<Course> courses = courseFILEMgr.loadFromFile();

                    for (Course course : courses) {
                        if (course.getCourseID().equals(courseID)) {
                            currentCourse = course;
                            break;
                        }
                    }
                    courseRegistrations.add(new CourseRegistration(currentStudent, currentCourse, tokens[lectureGroupInRegistrationIndex], tokens[tutorialGroupInRegistrationIndex], tokens[labGroupInRegistrationIndex]));
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurs when loading course registrations.");
            e.printStackTrace();
        } finally {
            printFinallyBlock(fileReader);
        }
        return courseRegistrations;
    }
}
