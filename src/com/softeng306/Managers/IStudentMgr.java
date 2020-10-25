package com.softeng306.Managers;

import com.softeng306.Student;

import java.util.ArrayList;

public interface IStudentMgr {

    void addStudent();

    void printAllStudents();

    boolean checkValidPersonNameInput(String personName);

    boolean checkValidStudentIDInput(String studentID);

    void printAllGender();

    ArrayList<String> getAllGender();

    boolean checkGenderValidation(String Gender);

    Student checkStudentExists();

    void setCourseMgr(ICourseMgr courseMgr);

}
