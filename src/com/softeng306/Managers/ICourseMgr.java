package com.softeng306.Managers;

import com.softeng306.Course;

import java.util.List;

public interface ICourseMgr {

    void addCourse();

    void checkAvailableSlots();

    void enterCourseWorkComponentWeightage(Course currentCourse);

    void printCourses();

    void printAllCourses();

    List<String> getAllCourseType();

    List<String> printCourseInDepartment(String department);

    boolean checkCourseTypeValidation(String courseType);

    boolean checkValidCourseIDInput(String courseID);

    boolean checkDepartmentValidation(String department);

    boolean checkValidGroupNameInput(String groupName);

    String checkCourseDepartmentExists();

    void printAllDepartments();

    List<String> getAllDepartment();

    Course checkCourseExists();

    List<Course> getCourses();

    void setProfessorMgr(IProfessorMgr professorMgr);
}
