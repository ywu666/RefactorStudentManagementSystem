package com.softeng306.Managers;

import java.util.List;

public interface ICourseMgr {

    void addCourse();

    void checkAvailableSlots();

    void enterCourseWorkComponentWeightage();

    void printCourses();

    void printAllCourses();

    List<String> getAllCourseType();

    List<String> printCourseInDepartment();

    String printGroupWithVacancyInfo();

    boolean checkCourseTypeValidation();

    boolean checkValidCourseIDValidation();

    boolean checkDepartmentValidation();

    boolean checkValidGroupNameInputValidation();

    boolean checkCourseDepartmentExists();

    void printAllDepartments();

    List<String> getAllDepartment();
}
