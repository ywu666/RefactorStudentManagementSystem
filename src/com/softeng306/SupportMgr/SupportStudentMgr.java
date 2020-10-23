package com.softeng306.SupportMgr;

import com.softeng306.HelpInfoMgr;
import com.softeng306.Main;
import com.softeng306.Student;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SupportStudentMgr extends SupportMgr {

    public Student checkExist(String studentID) {
        List<Student> anyStudent = Main.students.stream().filter(s->studentID.equals(s.getStudentID())).collect(Collectors.toList());
        if(anyStudent.size() == 0){
            return null;
        }
        System.out.println("Sorry. The student ID is used. This student already exists.");
        return anyStudent.get(0);
    }

    @Override
    public boolean checkValidIDInput(String studentID) {
        String REGEX = "^U[0-9]{7}[A-Z]$";
        boolean valid = Pattern.compile(REGEX).matcher(studentID).matches();
        if(!valid){
            System.out.println("Wrong format of student ID.");
        }
        return valid;
    }

    /**
     * Checks whether the inputted gender is valid.
     * @param gender The inputted gender.
     * @return boolean indicates whether the inputted gender is valid.
     */
    public static boolean checkGenderValidation(String gender){
        if(HelpInfoMgr.getAllGender().contains(gender)){
            return true;
        }
        System.out.println("The gender is invalid. Please re-enter.");
        return false;
    }
}
