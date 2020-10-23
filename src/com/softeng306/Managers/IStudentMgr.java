package com.softeng306.Managers;

import java.util.ArrayList;

public interface IStudentMgr {

    void addStudent();

    void printAllStudents();

    boolean checkValidPersonNameInputValidation();

    boolean checkValidStudentIDValidation();

    void printAllGender();

    ArrayList<String> getAllGender();

    boolean checkGenderValidation();

    String generateStudentID();
}
