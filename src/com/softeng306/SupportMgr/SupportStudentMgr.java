package com.softeng306.SupportMgr;

import com.softeng306.Entities.Student;
import com.softeng306.Enum.Gender;
import com.softeng306.Managers.IStudentMgr;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class SupportStudentMgr extends SupportDepartmentMgr {

    private IStudentMgr studentMgr;

    public SupportStudentMgr(IStudentMgr studentMgr) {
        this.studentMgr = studentMgr;
    }

    public boolean checkValidStudentIDInput(String studentID) {
        String REGEX = "^U[0-9]{7}[A-Z]$";
        boolean valid = Pattern.compile(REGEX).matcher(studentID).matches();
        if (!valid) {
            System.out.println("Wrong format of student ID.");
        }
        return valid;
    }


    /**
     * Checks whether the inputted person name is in the correct format.
     * This person can be professor or student.
     *
     * @param personName The inputted person name.
     * @return boolean indicates whether the inputted person name is valid.
     */
    public boolean checkValidPersonNameInput(String personName) {
        String REGEX = "^[ a-zA-Z]+$";
        boolean valid = Pattern.compile(REGEX).matcher(personName).matches();
        if (!valid) {
            System.out.println("Wrong format of name.");
        }
        return valid;
    }

    /**
     * Checks whether the inputted gender is valid.
     *
     * @param gender The inputted gender.
     * @return boolean indicates whether the inputted gender is valid.
     */
    public boolean checkGenderValidation(String gender) {
        if (getAllGender().contains(gender)) {
            return true;
        }
        System.out.println("The gender is invalid. Please re-enter.");
        return false;
    }

    /**
     * Displays a list of all the genders.
     */
    public void printAllGender() {
        int index = 1;
        for (Gender gender : Gender.values()) {
            System.out.println(index + ": " + gender);
            index++;
        }
    }

    /**
     * Gets all the genders as an array list.
     *
     * @return an array list of all the genders.
     */
    public List<String> getAllGender() {
        Set<Gender> genderEnumSet = EnumSet.allOf(Gender.class);
        List<String> genderStringList = new ArrayList<String>(0);
        Iterator iter = genderEnumSet.iterator();
        while (iter.hasNext()) {
            genderStringList.add(iter.next().toString());
        }
        return genderStringList;
    }

    /**
     * Displays a list of IDs of all the students.
     */
    public void printAllStudents() {
        studentMgr.getStudents().stream().map(s -> s.getStudentID()).forEach(System.out::println);
    }

    /**
     * Checks whether this student ID is used by other students.
     *
     * @param studentID This student's ID.
     * @return the existing student or else null.
     */
    public Student checkStudentExists(String studentID) {
        List<Student> anyStudent = studentMgr.getStudents().stream().filter(s -> studentID.equals(s.getStudentID())).collect(Collectors.toList());
        if (anyStudent.size() == 0) {
            return null;
        }
        System.out.println("Sorry. The student ID is used. This student already exists.");
        return anyStudent.get(0);
    }

    /**
     * Prompts the user to input an existing student.
     *
     * @return the inputted student.
     */
    public Student checkStudentExists() {
        String studentID;
        Student currentStudent = null;
        while (true) {
            System.out.println("Enter Student ID (-h to print all the student ID):");
            studentID = scanner.nextLine();
            while ("-h".equals(studentID)) {
                printAllStudents();
                studentID = scanner.nextLine();
            }

            System.setOut(dummyStream);
            currentStudent = checkStudentExists(studentID);
            System.setOut(originalStream);
            if (currentStudent == null) {
                System.out.println("Invalid Student ID. Please re-enter.");
            } else {
                break;
            }

        }
        return currentStudent;
    }
}
