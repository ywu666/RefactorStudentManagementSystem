package com.softeng306.SupportMgr;

import com.softeng306.Enum.Gender;
import com.softeng306.Main;

import java.util.*;
import java.util.regex.Pattern;


public class SupportStudentMgr extends SupportMgr {

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
        if(getAllGender().contains(gender)){
            return true;
        }
        System.out.println("The gender is invalid. Please re-enter.");
        return false;
    }

    /**
     * Displays a list of IDs of all the students.
     */
    public static void printAllStudents() {
        Main.students.stream().map(s -> s.getStudentID()).forEach(System.out::println);
    }

    /**
     * Displays a list of all the genders.
     */
    public static void printAllGender() {
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
    public static List<String> getAllGender() {
        Set<Gender> genderEnumSet = EnumSet.allOf(Gender.class);
        List<String> genderStringList = new ArrayList<String>(0);
        Iterator iter = genderEnumSet.iterator();
        while (iter.hasNext()) {
            genderStringList.add(iter.next().toString());
        }
        return genderStringList;
    }
}
